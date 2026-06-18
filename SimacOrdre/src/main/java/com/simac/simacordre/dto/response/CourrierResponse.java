package com.simac.simacordre.dto.response;

import com.simac.simacordre.enums.PrioriteEnum;
import com.simac.simacordre.enums.StatutCourrierEnum;
import com.simac.simacordre.enums.TypeCourrierEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CourrierResponse {

    private Long id;
    private String numeroOrdre;
    private String objet;
    private String expediteur;
    private String destination;
    private TypeCourrierEnum type;
    private StatutCourrierEnum statut;
    private PrioriteEnum priorite;
    private LocalDate dateReception;
    private LocalDate dateLimite;
    private String description;
    private Boolean archive;
    private String createurNomComplet;
    private String departementNom;
    private LocalDateTime createdAt;

    public CourrierResponse(
            Long id,
            String numeroOrdre,
            String objet,
            String expediteur,
            String destination,
            TypeCourrierEnum type,
            StatutCourrierEnum statut,
            PrioriteEnum priorite,
            LocalDate dateReception,
            LocalDate dateLimite,
            String description,
            Boolean archive,
            String createurNomComplet,
            String departementNom,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.numeroOrdre = numeroOrdre;
        this.objet = objet;
        this.expediteur = expediteur;
        this.destination = destination;
        this.type = type;
        this.statut = statut;
        this.priorite = priorite;
        this.dateReception = dateReception;
        this.dateLimite = dateLimite;
        this.description = description;
        this.archive = archive;
        this.createurNomComplet = createurNomComplet;
        this.departementNom = departementNom;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public String getObjet() {
        return objet;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getDestination() {
        return destination;
    }

    public TypeCourrierEnum getType() {
        return type;
    }

    public StatutCourrierEnum getStatut() {
        return statut;
    }

    public PrioriteEnum getPriorite() {
        return priorite;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public LocalDate getDateLimite() {
        return dateLimite;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getArchive() {
        return archive;
    }

    public String getCreateurNomComplet() {
        return createurNomComplet;
    }

    public String getDepartementNom() {
        return departementNom;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}