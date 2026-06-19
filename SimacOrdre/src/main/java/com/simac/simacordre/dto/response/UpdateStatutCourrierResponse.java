package com.simac.simacordre.dto.response;

import com.simac.simacordre.enums.StatutCourrierEnum;

import java.time.LocalDateTime;

public class UpdateStatutCourrierResponse {

    private Long courrierId;
    private String numeroOrdre;
    private StatutCourrierEnum ancienStatut;
    private StatutCourrierEnum nouveauStatut;
    private Long modifieParId;
    private String modifieParNomComplet;
    private String commentaire;
    private LocalDateTime dateModification;
    private String message;

    public UpdateStatutCourrierResponse(
            Long courrierId,
            String numeroOrdre,
            StatutCourrierEnum ancienStatut,
            StatutCourrierEnum nouveauStatut,
            Long modifieParId,
            String modifieParNomComplet,
            String commentaire,
            LocalDateTime dateModification,
            String message
    ) {
        this.courrierId = courrierId;
        this.numeroOrdre = numeroOrdre;
        this.ancienStatut = ancienStatut;
        this.nouveauStatut = nouveauStatut;
        this.modifieParId = modifieParId;
        this.modifieParNomComplet = modifieParNomComplet;
        this.commentaire = commentaire;
        this.dateModification = dateModification;
        this.message = message;
    }

    public Long getCourrierId() {
        return courrierId;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public StatutCourrierEnum getAncienStatut() {
        return ancienStatut;
    }

    public StatutCourrierEnum getNouveauStatut() {
        return nouveauStatut;
    }

    public Long getModifieParId() {
        return modifieParId;
    }

    public String getModifieParNomComplet() {
        return modifieParNomComplet;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public String getMessage() {
        return message;
    }
}