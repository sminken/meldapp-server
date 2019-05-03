package nl.ou.applab.meldapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasse verantwoordelijk voor het testen van de klasse Melding
 */
class MeldingTest {

    private Melding melding;

    /**
     * Initialiseert een melding ten behoeve van de testen
     */
    @BeforeEach
    void setUp() {
        melding = new Melding(
                "1234",
                "6666",
                "systeem start niet op",
                "Er is een probleem met de stroomvoorziening",
                "13-07-2018 11:57:24",
                "OPEN",
                false);
    }

    /**
     * Initialiseert Melding op null tussen de testen
     */
    @AfterEach
    void tearDown() {
        melding = null;
    }

    /**
     * Test of de juiste naam van de gebruiker wordt geretourneerd
     */
    @Test
    void getUidGebruikerMelder() {
        String uidGebruikerMelder = melding.getUidGebruikerMelder();
        assertEquals(uidGebruikerMelder, "1234");
        assertNotEquals("Steven van de OU", uidGebruikerMelder);
    }

    /**
     * Test of de juiste naam van de gebruiker wordt geretourneerd
     */
    @Test
    void getUidGebruikerBehandelaar() {
        String uidGebruikerBehandelaar = melding.getUidGebruikerBehandelaar();
        assertEquals(uidGebruikerBehandelaar, "6666");
        assertNotEquals("1234", uidGebruikerBehandelaar);
    }

    /**
     * Test of de juiste boolean voor verzonden notificatie wordt geretourneerd
     */
    @Test
    void getNotificatieVerstuurd() {
        Boolean verstuurd = melding.getNotificatieVerstuurd();
        assertEquals(false, verstuurd);
        assertNotEquals(true, verstuurd);
    }

    /**
     * Test of het juiste onderwerp wordt geretourneerd
     */
    @Test
    void getOnderwerp() {
        String onderwerp = melding.getOnderwerp();
        assertEquals("systeem start niet op", onderwerp);
        assertNotEquals("windows update vastgelopen", onderwerp);
    }

    /**
     * Test of de juiste inhoud wordt geretourneerd
     */
    @Test
    void getInhoud() {
        String inhoud = melding.getInhoud();
        String verwachteInhoud = "Er is een probleem met de stroomvoorziening";
        String verkeerdeInhoud = "De organisatie moet reorganiseren, alle ICT medewerkers worden ontslagen";

        assertEquals(verwachteInhoud, inhoud);
        assertNotEquals(inhoud, verkeerdeInhoud);
    }

    /**
     * Test of de juiste status wordt geretourneerd
     */
    @Test
    void getStatus() {
        String status = melding.getStatus();

        assertEquals("OPEN", status);
        assertNotEquals("GESLOTEN", status);
    }

    /**
     * Test of de juiste datum en tijd worden geretourneerd
     */
    @Test
    void getDatumTijd() {
        String datumTijd = melding.getDatumTijd();
        assertEquals("13-07-2018 11:57:24", datumTijd);
        assertNotEquals("13-03-2021 11:44:24", datumTijd);
    }

    /**
     * Test of de juiste melding.toString() wordt geretourneerd
     */
    @Test
    void toStringTest() {
        assertEquals("systeem start niet op Er is een probleem met de stroomvoorziening", melding.toString());
    }
}