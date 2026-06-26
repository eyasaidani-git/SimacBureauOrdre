package com.simac.simacordre.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_import_log")
public class EmailImportLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", nullable = false, unique = true, length = 500)
    private String messageId;

    @Column(name = "dossier_gmail", nullable = false, length = 100)
    private String dossierGmail;

    @Column(length = 500)
    private String sujet;

    @Column(length = 500)
    private String expediteur;

    @Column(name = "date_message")
    private LocalDateTime dateMessage;

    @Column(name = "importe_comme", nullable = false, length = 50)
    private String importeComme;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public EmailImportLog() {
    }

    public Long getId() {
        return id;
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

    public String getImporteComme() {
        return importeComme;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setImporteComme(String importeComme) {
        this.importeComme = importeComme;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}