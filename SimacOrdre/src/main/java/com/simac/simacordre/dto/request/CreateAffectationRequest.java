package com.simac.simacordre.dto.request;

public class CreateAffectationRequest {

    private Long courrierId;
    private Long affecteParId;
    private Long affecteAUtilisateurId;
    private Long affecteADepartementId;
    private String commentaire;

    public Long getCourrierId() {
        return courrierId;
    }

    public Long getAffecteParId() {
        return affecteParId;
    }

    public Long getAffecteAUtilisateurId() {
        return affecteAUtilisateurId;
    }

    public Long getAffecteADepartementId() {
        return affecteADepartementId;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCourrierId(Long courrierId) {
        this.courrierId = courrierId;
    }

    public void setAffecteParId(Long affecteParId) {
        this.affecteParId = affecteParId;
    }

    public void setAffecteAUtilisateurId(Long affecteAUtilisateurId) {
        this.affecteAUtilisateurId = affecteAUtilisateurId;
    }

    public void setAffecteADepartementId(Long affecteADepartementId) {
        this.affecteADepartementId = affecteADepartementId;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}