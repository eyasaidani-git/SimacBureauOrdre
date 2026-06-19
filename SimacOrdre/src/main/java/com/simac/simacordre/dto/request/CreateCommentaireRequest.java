package com.simac.simacordre.dto.request;

public class CreateCommentaireRequest {

    private Long courrierId;
    private Long auteurId;
    private String contenu;

    public CreateCommentaireRequest() {
    }

    public Long getCourrierId() {
        return courrierId;
    }

    public void setCourrierId(Long courrierId) {
        this.courrierId = courrierId;
    }

    public Long getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(Long auteurId) {
        this.auteurId = auteurId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}