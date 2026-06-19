package com.simac.simacordre.dto.response;

import com.simac.simacordre.enums.TypeNotificationEnum;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private Long courrierId;
    private String numeroOrdre;
    private Long utilisateurId;
    private String utilisateurNomComplet;
    private String titre;
    private String message;
    private TypeNotificationEnum type;
    private Boolean lue;
    private LocalDateTime createdAt;

    public NotificationResponse(
            Long id,
            Long courrierId,
            String numeroOrdre,
            Long utilisateurId,
            String utilisateurNomComplet,
            String titre,
            String message,
            TypeNotificationEnum type,
            Boolean lue,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.courrierId = courrierId;
        this.numeroOrdre = numeroOrdre;
        this.utilisateurId = utilisateurId;
        this.utilisateurNomComplet = utilisateurNomComplet;
        this.titre = titre;
        this.message = message;
        this.type = type;
        this.lue = lue;
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

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public String getUtilisateurNomComplet() {
        return utilisateurNomComplet;
    }

    public String getTitre() {
        return titre;
    }

    public String getMessage() {
        return message;
    }

    public TypeNotificationEnum getType() {
        return type;
    }

    public Boolean getLue() {
        return lue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}