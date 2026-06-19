package com.simac.simacordre.dto.request;

import com.simac.simacordre.enums.TypeNotificationEnum;

public class CreateNotificationRequest {

    private Long courrierId;
    private Long utilisateurId;
    private String titre;
    private String message;
    private TypeNotificationEnum type;

    public CreateNotificationRequest() {
    }

    public Long getCourrierId() {
        return courrierId;
    }

    public void setCourrierId(Long courrierId) {
        this.courrierId = courrierId;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeNotificationEnum getType() {
        return type;
    }

    public void setType(TypeNotificationEnum type) {
        this.type = type;
    }
}