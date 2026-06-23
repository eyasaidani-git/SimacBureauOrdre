package com.simac.simacordre.dto.request;

import java.time.LocalDateTime;

public class CreateEmailSpamRequest {

    private String expediteur;
    private String objet;
    private String contenu;
    private Boolean confirmeSpam;
    private LocalDateTime dateReception;
    private Long traiteParId;
    private Long responsableConcerneId;

    public CreateEmailSpamRequest() {
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Boolean getConfirmeSpam() {
        return confirmeSpam;
    }

    public void setConfirmeSpam(Boolean confirmeSpam) {
        this.confirmeSpam = confirmeSpam;
    }

    public LocalDateTime getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDateTime dateReception) {
        this.dateReception = dateReception;
    }

    public Long getTraiteParId() {
        return traiteParId;
    }

    public void setTraiteParId(Long traiteParId) {
        this.traiteParId = traiteParId;
    }

    public Long getResponsableConcerneId() {
        return responsableConcerneId;
    }

    public void setResponsableConcerneId(Long responsableConcerneId) {
        this.responsableConcerneId = responsableConcerneId;
    }
}