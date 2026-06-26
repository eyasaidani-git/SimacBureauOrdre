package com.simac.simacordre.dto.response;

import java.time.LocalDateTime;

public class GmailMessagePreviewResponse {

    private String messageId;
    private String dossierGmail;
    private String sujet;
    private String expediteur;
    private LocalDateTime dateMessage;
    private String apercuContenu;
    private Boolean spamDossier;

    public GmailMessagePreviewResponse() {
    }

    public GmailMessagePreviewResponse(
            String messageId,
            String dossierGmail,
            String sujet,
            String expediteur,
            LocalDateTime dateMessage,
            String apercuContenu,
            Boolean spamDossier
    ) {
        this.messageId = messageId;
        this.dossierGmail = dossierGmail;
        this.sujet = sujet;
        this.expediteur = expediteur;
        this.dateMessage = dateMessage;
        this.apercuContenu = apercuContenu;
        this.spamDossier = spamDossier;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getDossierGmail() {
        return dossierGmail;
    }

    public String getSujet() {
        return sujet;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public LocalDateTime getDateMessage() {
        return dateMessage;
    }

    public String getApercuContenu() {
        return apercuContenu;
    }

    public Boolean getSpamDossier() {
        return spamDossier;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setDossierGmail(String dossierGmail) {
        this.dossierGmail = dossierGmail;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public void setDateMessage(LocalDateTime dateMessage) {
        this.dateMessage = dateMessage;
    }

    public void setApercuContenu(String apercuContenu) {
        this.apercuContenu = apercuContenu;
    }

    public void setSpamDossier(Boolean spamDossier) {
        this.spamDossier = spamDossier;
    }
}