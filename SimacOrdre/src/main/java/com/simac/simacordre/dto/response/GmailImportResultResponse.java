package com.simac.simacordre.dto.response;

public class GmailImportResultResponse {

    private int emailsLus;
    private int courriersImportes;
    private int spamsImportes;
    private int doublonsIgnores;

    public GmailImportResultResponse() {
    }

    public GmailImportResultResponse(
            int emailsLus,
            int courriersImportes,
            int spamsImportes,
            int doublonsIgnores
    ) {
        this.emailsLus = emailsLus;
        this.courriersImportes = courriersImportes;
        this.spamsImportes = spamsImportes;
        this.doublonsIgnores = doublonsIgnores;
    }

    public int getEmailsLus() {
        return emailsLus;
    }

    public int getCourriersImportes() {
        return courriersImportes;
    }

    public int getSpamsImportes() {
        return spamsImportes;
    }

    public int getDoublonsIgnores() {
        return doublonsIgnores;
    }

    public void setEmailsLus(int emailsLus) {
        this.emailsLus = emailsLus;
    }

    public void setCourriersImportes(int courriersImportes) {
        this.courriersImportes = courriersImportes;
    }

    public void setSpamsImportes(int spamsImportes) {
        this.spamsImportes = spamsImportes;
    }

    public void setDoublonsIgnores(int doublonsIgnores) {
        this.doublonsIgnores = doublonsIgnores;
    }
}