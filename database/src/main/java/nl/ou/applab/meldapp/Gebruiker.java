package nl.ou.applab.meldapp;

/**
 * Klasse verantwoordelijk voor het beheren van de instanties van de  Gebruiker
 *
 * @author Steven Minken
 */

class Gebruiker {


    private String voornaam;
    private String achternaam;
    private String organisatie;
    private String uid;
    private String status;
    private String rol;
    private String telefoonnummer;
    private String deviceToken;
    private boolean notification;

    // constructors
    public Gebruiker() {
    }

    public Gebruiker(String voornaam, String achternaam, String organisatie, String uid,
                     String status, String rol, String telefoonnummer,
                     boolean notification, String deviceToken) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.organisatie = organisatie;
        this.uid = uid;
        this.status = status;
        this.rol = rol;
        this.telefoonnummer = telefoonnummer;
        this.notification = notification;
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public boolean getNotification() {
        return notification;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getOrganisatie() {
        return organisatie;
    }

    public String getUid() {
        return uid;
    }

    public String getStatus() {
        return status;
    }

    public String getRol() {
        return rol;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    /*
    Override standard methods from Object
     */

    @Override
    public String toString() {
        return voornaam + " " + achternaam + " " + organisatie + " " + telefoonnummer;
    }
}
