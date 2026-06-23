package com.simac.simacordre.dto.response;

import java.time.LocalDateTime;

public class EmailSpamResponse {

    private Long id;
    private String expediteur;
    private String objet;
    private String contenu;
    private Boolean confirmeSpam;
    private LocalDateTime dateReception;
    private Long traiteParId;
    private String traiteParNomComplet;
    private Long responsableId;
    private String responsableNomComplet;
    private LocalDateTime createdAt;

    public EmailSpamResponse(
            Long id,
            String expediteur,
            String objet,
            String contenu,
            Boolean confirmeSpam,
            LocalDateTime dateReception,
            Long traiteParId,
            String traiteParNomComplet,
            Long responsableId,
            String responsableNomComplet,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.expediteur = expediteur;
        this.objet = objet;
        this.contenu = contenu;
        this.confirmeSpam = confirmeSpam;
        this.dateReception = dateReception;
        this.traiteParId = traiteParId;
        this.traiteParNomComplet = traiteParNomComplet;
        this.responsableId = responsableId;
        this.responsableNomComplet = responsableNomComplet;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getObjet() {
        return objet;
    }

    public String getContenu() {
        return contenu;
    }

    public Boolean getConfirmeSpam() {
        return confirmeSpam;
    }

    public LocalDateTime getDateReception() {
        return dateReception;
    }

    public Long getTraiteParId() {
        return traiteParId;
    }

    public String getTraiteParNomComplet() {
        return traiteParNomComplet;
    }

    public Long getResponsableId() {
        return responsableId;
    }

    public String getResponsableNomComplet() {
        return responsableNomComplet;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}