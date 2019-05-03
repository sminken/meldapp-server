package nl.ou.applab.meldapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasse verantwoordelijk voor het testen van de klasse Gebruiker
 */
class GebruikerTest {

    private Gebruiker gebruiker;

    /**
     * Initialiseert een Gebruiker ten behoeve van de testen
     */
    @BeforeEach
    void setUp() {
        gebruiker = new Gebruiker("testVoornaam", "testAchternaam", "testOrganisatie", "12345",
                "ACTIEF", "MELDER", "0612345678", true, "66666");
    }

    /**
     * Initialiseert Melding op null tussen de testen
     */
    @AfterEach
    void tearDown() {
        gebruiker = null;
    }

    /**
     * Test of de het juiste token wordt geretourneerd
     */
    @Test
    void getDeviceToken() {
        assertEquals("66666", gebruiker.getDeviceToken());
        assertNotEquals("55555", gebruiker.getDeviceToken());
    }

    /**
     * Test of het juiste boolean voor verzonden notificatie aan de gebruiker wordt geretourneerd
     */
    @Test
    void getNotification() {
        assertTrue(gebruiker.getNotification());
        assertFalse(!gebruiker.getNotification());
    }

    /**
     * Test of de juiste voornaam van de gebruiker wordt geretourneerd
     */
    @Test
    void getVoornaam() {
        assertEquals("testVoornaam", gebruiker.getVoornaam());
        assertNotEquals("Joris", gebruiker.getVoornaam());
    }

    /**
     * Test of de juiste achternaam van de gebruiker wordt geretourneerd
     */
    @Test
    void getAchternaam() {
        assertEquals("testAchternaam", gebruiker.getAchternaam());
        assertNotEquals("van Gemert", gebruiker.getAchternaam());
    }

    /**
     * Test of de juiste organisatie van de gebruiker wordt geretourneerd
     */
    @Test
    void getOrganisatie() {
        assertEquals("testOrganisatie", gebruiker.getOrganisatie());
        assertNotEquals("Philips", gebruiker.getOrganisatie());
    }

    /**
     * Test of de het juiste uid van de gebruiker wordt geretourneerd
     */
    @Test
    void getUid() {
        assertEquals("12345", gebruiker.getUid());
        assertNotEquals("54321", gebruiker.getUid());
    }

    /**
     * Test of de juiste status van de gebruiker wordt geretourneerd
     */
    @Test
    void getStatus() {
        assertEquals("ACTIEF", gebruiker.getStatus());
        assertNotEquals("INACTIEF", gebruiker.getStatus());
    }

    /**
     * Test of de juiste rol van de gebruiker wordt geretourneerd
     */
    @Test
    void getRol() {
        assertEquals("MELDER", gebruiker.getRol());
        assertNotEquals("BEHANDELAAR", gebruiker.getRol());
    }

    /**
     * Test of het juiste telefoonnummer van de gebruiker wordt geretourneerd
     */
    @Test
    void getTelefoonnummer() {
        assertEquals("0612345678", gebruiker.getTelefoonnummer());
        assertNotEquals("89790", gebruiker.getTelefoonnummer());
    }

    /**
     * Test of de toString() van de gebruiker wordt geretourneerd
     */
    @Test
    void toStringTest() {
        assertEquals("testVoornaam testAchternaam testOrganisatie 0612345678", gebruiker.toString());
        assertNotEquals("Joris Janssen 243243", gebruiker.toString());
    }
}