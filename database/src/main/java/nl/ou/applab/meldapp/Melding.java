package nl.ou.applab.meldapp;


/**
 * Klasse verantwoordelijk voor het beheer van instanties van Meldingen
 *
 * @author Steven Minken
 */
class Melding {

    private String uidGebruikerMelder;
    private String uidGebruikerBehandelaar;
    private String onderwerp;
    private String inhoud;
    private String status;
    private String datumTijd;
    private boolean notificatieVerstuurd;

    public Melding() {
    }

    /**
     * Constructor voor de Melding
     *
     * @param uidGebruikerMelder Uid van de gebruiker
     * @param onderwerp          onderwerp van de melding
     * @param inhoud             inhoud van de melding
     * @param datumTijd          datum en tijd van de melding
     * @param status             status van de melding
     */
    public Melding(String uidGebruikerMelder, String uidGebruikerBehandelaar, String onderwerp, String inhoud, String datumTijd, String status, boolean notificatieVerstuurd) {
        this.uidGebruikerMelder = uidGebruikerMelder;
        this.uidGebruikerBehandelaar = uidGebruikerBehandelaar;
        this.onderwerp = onderwerp;
        this.inhoud = inhoud;
        this.datumTijd = datumTijd;
        this.status = status;
        this.notificatieVerstuurd = notificatieVerstuurd;

    }

    /**
     * Retourneert uid van de melder
     *
     * @return String uid van de melder
     */
    public String getUidGebruikerMelder() {
        return uidGebruikerMelder;
    }

    /**
     * Retourneert de uid van de behandelaar
     *
     * @return String uid van de behandelaar
     */
    public String getUidGebruikerBehandelaar() {
        return uidGebruikerBehandelaar;
    }

    /**
     * Retourneert uid van de melder
     *
     * @return String uid van de melder
     */
    public boolean getNotificatieVerstuurd() {
        return notificatieVerstuurd;
    }

    /**
     * Retourneert onderwerp van de melding
     *
     * @return String onderwerp van de melding
     */
    public String getOnderwerp() {
        return onderwerp;
    }

    /**
     * Retourneert inhoud van de melding
     *
     * @return String inhoud van de melding
     */
    public String getInhoud() {
        return inhoud;
    }

    /**
     * Retourneert status van de melding
     *
     * @return String status van de melding
     */
    public String getStatus() {
        return status;
    }

    /**
     * Retourneert datum en tijd van de melding
     *
     * @return String datum en tijd van de melding
     */
    public String getDatumTijd() {
        return datumTijd;
    }

    /*
    Override standard methods from Object
     */

    @Override
    public String toString() {
        return onderwerp + " " + inhoud;
    }
}

