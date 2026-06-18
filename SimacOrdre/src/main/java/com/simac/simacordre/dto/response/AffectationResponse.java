package com.simac.simacordre.dto.response;

import java.time.LocalDateTime;

public class AffectationResponse {

    private Long id;
    private Long courrierId;
    private String numeroOrdre;
    private String affecteParNomComplet;
    private String affecteAUtilisateurNomComplet;
    private String affecteADepartementNom;
    private String commentaire;
    private Boolean active;
    private LocalDateTime createdAt;

    public AffectationResponse(
            Long id,
            Long courrierId,
            String numeroOrdre,
            String affecteParNomComplet,
            String affecteAUtilisateurNomComplet,
            String affecteADepartementNom,
            String commentaire,
            Boolean active,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.courrierId = courrierId;
        this.numeroOrdre = numeroOrdre;
        this.affecteParNomComplet = affecteParNomComplet;
        this.affecteAUtilisateurNomComplet = affecteAUtilisateurNomComplet;
        this.affecteADepartementNom = affecteADepartementNom;
        this.commentaire = commentaire;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCourrierId() {
        return courrierId;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public String getAffecteParNomComplet() {
        return affecteParNomComplet;
    }

    public String getAffecteAUtilisateurNomComplet() {
        return affecteAUtilisateurNomComplet;
    }

    public String getAffecteADepartementNom() {
        return affecteADepartementNom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}