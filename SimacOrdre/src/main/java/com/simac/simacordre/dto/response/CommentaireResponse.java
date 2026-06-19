package com.simac.simacordre.dto.response;

import java.time.LocalDateTime;

public class CommentaireResponse {

    private Long id;
    private Long courrierId;
    private String numeroOrdre;
    private Long auteurId;
    private String auteurNomComplet;
    private String contenu;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentaireResponse(
            Long id,
            Long courrierId,
            String numeroOrdre,
            Long auteurId,
            String auteurNomComplet,
            String contenu,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.courrierId = courrierId;
        this.numeroOrdre = numeroOrdre;
        this.auteurId = auteurId;
        this.auteurNomComplet = auteurNomComplet;
        this.contenu = contenu;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Long getAuteurId() {
        return auteurId;
    }

    public String getAuteurNomComplet() {
        return auteurNomComplet;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}