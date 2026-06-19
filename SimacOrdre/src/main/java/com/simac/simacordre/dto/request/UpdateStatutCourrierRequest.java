package com.simac.simacordre.dto.request;

import com.simac.simacordre.enums.StatutCourrierEnum;

public class UpdateStatutCourrierRequest {

    private StatutCourrierEnum nouveauStatut;
    private Long modifieParId;
    private String commentaire;

    public UpdateStatutCourrierRequest() {
    }

    public StatutCourrierEnum getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(StatutCourrierEnum nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    public Long getModifieParId() {
        return modifieParId;
    }

    public void setModifieParId(Long modifieParId) {
        this.modifieParId = modifieParId;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}