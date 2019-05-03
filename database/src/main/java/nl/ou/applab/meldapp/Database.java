package nl.ou.applab.meldapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.messaging.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Klasse inclusief main methode verantwoordelijk voor het beheer van de Firebase SDK
 * ten behoeve van het MeldApp project
 *
 * @author Steven Minken
 */
class Database {

    private static final String DATABASE_URL = "https://oumeldapp-f9d31.firebaseio.com/";

    private List<Gebruiker> gebruikerLijst;
    private List<Melding> meldingenLijst;

    /**
     * Constructor voor de database
     * opmerking: Initialiseert luisteraars ten behoeve van het wijzigen van de status van de melding naar
     * OPGELOST in de database en past de waarde notificatieVerstuurd aan bij status OPGELOST en status HEROPEND
     *
     * @param database referntie naar database
     */
    private Database(DatabaseReference database) {

//        luisteraar op de database om wijziging van de melding naar OPGELOST te registreren
        database.child("meldingen").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                Melding melding = dataSnapshot.getValue(Melding.class);

                if (melding.getStatus().equals("OPGELOST")) {

                    String melderuid = melding.getUidGebruikerMelder();
                    boolean notification = false;
                    boolean notificatieVerstuurd = melding.getNotificatieVerstuurd();
                    String meldinguid = dataSnapshot.getKey();
                    String token = "";
                    List<Gebruiker> gebruikerLijstKopie = gebruikerLijst;

                    if (gebruikerLijstKopie != null) {
                        for (Gebruiker gebruiker : gebruikerLijstKopie) {
                            if (gebruiker.getUid().equals(melderuid)) {
                                token = gebruiker.getDeviceToken();
                                notification = gebruiker.getNotification();
                                break;
                            }
                        }
                    }

                    if (!(token == null || token.equals("")) && notification && !notificatieVerstuurd) {
                        verstuurMelding(melding, token, meldinguid, database);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        luisteraar op de database om lijst gebruikers bij te houden op de server
        database.child("gebruikers").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Gebruiker> list = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Gebruiker gebruiker = item.getValue(Gebruiker.class);
                    if (gebruiker != null) {
                        list.add(gebruiker);
                    }
                }
                gebruikerLijst = list;
            }

            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });

//        luisteraar op de database om lijst meldingen bij te houden op de server
        database.child("meldingen").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Melding> meldingLijst = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Melding melding = item.getValue(Melding.class);
                    if (melding != null) {
                        meldingLijst.add(melding);
                    }
                }
                meldingenLijst = meldingLijst;
            }

            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });

    }

    /**
     * Verstuurt de notificatie aan de gebruiker op basis van het token, de meldingUid en de referentie naar de database
     *
     * @param token      het token van de melding
     * @param meldingUid de uid van de melding
     */
    private void verstuurMelding(Melding melding, String token, String meldingUid, DatabaseReference database) {

        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000) // 1 hour in milliseconds
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                                .setTitle("Meldapp ")
                                .setBody("U heeft een nieuwe opgeloste melding.")
                                .build())
                        .putData("onderwerp", melding.getOnderwerp())
                        .build())
                .setToken(token)
                .build();

//      Verstuur een bericht naar een apparaat op basis van het geregistreerd token
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

//      onCompletionListener om mee te geven in databaseReferenceUpdate children
        DatabaseReference.CompletionListener onCompletion = (databaseError, databaseReference) -> {

        };

        DatabaseReference databaseReference = database.child("meldingen").child(meldingUid);

        Map<String, Object> notificatieVerstuurdUpdate = new HashMap<>();
        notificatieVerstuurdUpdate.put("notificatieVerstuurd", true);
        databaseReference.updateChildren(notificatieVerstuurdUpdate, onCompletion);
//		Print de responsecode in de console na versturen message
        System.out.println("Successfully sent message: " + response);

    }

    /**
     * Verstuurt de notificatie aan de gebruiker op basis van een melding, een token en een referentie naar de database
     *
     * @param melding de melding waarvan de notificatie wordt gestuurd
     * @param token   het token van de melding
     */
    private void verstuurMelding(Melding melding, String token, DatabaseReference database) {

        zoekMeldingKey(database, melding, new OnGetDataListener() {
            @SuppressWarnings({"unchecked", "LoopStatementThatDoesntLoop"})
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                for (String key : map.keySet()) {
                    verstuurMelding(null, token, key, database);
                    break;
                }
            }

            @Override
            public void onFailure() {
                System.out.println("error loading data from database");
            }
        });

    }

    /**
     * Zoekt de key van de melding in de firebase database
     *
     * @param database referentie naar de database
     * @param melding  de melding waarvan de key gezocht wordt
     * @param listener luisteraar voor het resultaat van de query
     */
    private void zoekMeldingKey(DatabaseReference database, Melding melding, final OnGetDataListener listener) {
//            listener.onStart();
        Query query = database.child("meldingen").orderByChild("datumTijd").equalTo(melding.getDatumTijd());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listener.onSuccess(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                listener.onFailure();
            }
        });
    }


    public static void main(String[] args) {

        // Initialize Firebase
        try {
            // [START initialize]
            FileInputStream serviceAccount = new FileInputStream("service-account.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
            // [END initialize]
        } catch (IOException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());

            System.exit(1);
        }

        Database database = new Database(FirebaseDatabase.getInstance().getReference());
        Runnable runnable = () -> {
//      Maakt een nieuwe referentie naar de lijsten gebruikers en meldingen
            List<Gebruiker> gebruikerLijstRunnable = database.gebruikerLijst;
            List<Melding> meldingLijstRunnable = database.meldingenLijst;

//      Doorzoekt de lijsten op status OPGELOST waarbij geen notificatie is verstuurd
            for (Melding m : meldingLijstRunnable) {
                if (m.getStatus().equals("OPGELOST") && !m.getNotificatieVerstuurd()) {
                    String uidMelder = m.getUidGebruikerMelder();
                    for (Gebruiker g : gebruikerLijstRunnable) {
                        if (g.getUid().equals(uidMelder) && g.getNotification()) {
                            database.verstuurMelding(m, g.getDeviceToken(), FirebaseDatabase.getInstance().getReference());
                        }
                    }
                }
            }

        };

//      ScheduledExecutorService checkt firebase om de 30 seconden voor OPGELOSTE meldingen waarbij
//      geen notificatie is verstuurd.
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 30, 30, TimeUnit.SECONDS);

//      wait forever
        final Semaphore shutdownLatch = new Semaphore(0);
        try {
            shutdownLatch.acquire(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
