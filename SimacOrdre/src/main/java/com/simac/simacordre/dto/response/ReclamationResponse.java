package com.simac.simacordre.dto.response;

import com.simac.simacordre.enums.CategorieReclamationEnum;
import com.simac.simacordre.enums.StatutReclamationEnum;

import java.time.LocalDateTime;

public class ReclamationResponse {

    private Long id;
    private CategorieReclamationEnum categorie;
    private StatutReclamationEnum statut;
    private String sujet;
    private String description;

    private Long courrierId;
    private String numeroOrdre;

    private Long createurId;
    private String createurNomComplet;
    private String createurRole;

    private Long traiteeParId;
    private String traiteeParNomComplet;
    private String traiteeParRole;

    private String reponse;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReclamationResponse(
            Long id,
            CategorieReclamationEnum categorie,
            StatutReclamationEnum statut,
            String sujet,
            String description,
            Long courrierId,
            String numeroOrdre,
            Long createurId,
            String createurNomComplet,
            String createurRole,
            Long traiteeParId,
            String traiteeParNomComplet,
            String traiteeParRole,
            String reponse,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.categorie = categorie;
        this.statut = statut;
        this.sujet = sujet;
        this.description = description;
        this.courrierId = courrierId;
        this.numeroOrdre = numeroOrdre;
        this.createurId = createurId;
        this.createurNomComplet = createurNomComplet;
        this.createurRole = createurRole;
        this.traiteeParId = traiteeParId;
        this.traiteeParNomComplet = traiteeParNomComplet;
        this.traiteeParRole = traiteeParRole;
        this.reponse = reponse;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public CategorieReclamationEnum getCategorie() {
        return categorie;
    }

    public StatutReclamationEnum getStatut() {
        return statut;
    }

    public String getSujet() {
        return sujet;
    }

    public String getDescription() {
        return description;
    }

    public Long getCourrierId() {
        return courrierId;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public Long getCreateurId() {
        return createurId;
    }

    public String getCreateurNomComplet() {
        return createurNomComplet;
    }

    public String getCreateurRole() {
        return createurRole;
    }

    public Long getTraiteeParId() {
        return traiteeParId;
    }

    public String getTraiteeParNomComplet() {
        return traiteeParNomComplet;
    }

    public String getTraiteeParRole() {
        return traiteeParRole;
    }

    public String getReponse() {
        return reponse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}