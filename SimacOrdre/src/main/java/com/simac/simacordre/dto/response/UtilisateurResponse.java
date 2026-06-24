package com.simac.simacordre.dto.response;

import com.simac.simacordre.enums.RoleEnum;
import com.simac.simacordre.enums.StatutUtilisateurEnum;

import java.time.LocalDateTime;

public class UtilisateurResponse {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private RoleEnum role;
    private StatutUtilisateurEnum statut;
    private Boolean premiereConnexion;
    private Boolean emailVerifie;
    private Long departementId;
    private String departementNom;
    private Long responsableId;
    private String responsableNomComplet;
    private String photoProfil;
    private LocalDateTime createdAt;

    public UtilisateurResponse(
            Long id,
            String nom,
            String prenom,
            String email,
            String telephone,
            RoleEnum role,
            StatutUtilisateurEnum statut,
            Boolean premiereConnexion,
            Boolean emailVerifie,
            Long departementId,
            String departementNom,
            Long responsableId,
            String responsableNomComplet,
            String photoProfil,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
        this.statut = statut;
        this.premiereConnexion = premiereConnexion;
        this.emailVerifie = emailVerifie;
        this.departementId = departementId;
        this.departementNom = departementNom;
        this.responsableId = responsableId;
        this.responsableNomComplet = responsableNomComplet;
        this.photoProfil = photoProfil;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public RoleEnum getRole() {
        return role;
    }

    public StatutUtilisateurEnum getStatut() {
        return statut;
    }

    public Boolean getPremiereConnexion() {
        return premiereConnexion;
    }

    public Boolean getEmailVerifie() {
        return emailVerifie;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public String getDepartementNom() {
        return departementNom;
    }

    public Long getResponsableId() {
        return responsableId;
    }

    public String getResponsableNomComplet() {
        return responsableNomComplet;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}