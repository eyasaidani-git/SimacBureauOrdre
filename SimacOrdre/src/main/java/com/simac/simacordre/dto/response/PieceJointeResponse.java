package com.simac.simacordre.dto.response;

import com.simac.simacordre.enums.TypeDocumentPieceJointeEnum;
import com.simac.simacordre.enums.TypeFichierEnum;

import java.time.LocalDateTime;

public class PieceJointeResponse {

    private Long id;
    private Long courrierId;
    private String numeroOrdre;
    private String nomFichier;
    private TypeFichierEnum typeFichier;
    private String cheminFichier;
    private Long taille;
    private TypeDocumentPieceJointeEnum typeDocument;
    private LocalDateTime uploadedAt;

    public PieceJointeResponse(
            Long id,
            Long courrierId,
            String numeroOrdre,
            String nomFichier,
            TypeFichierEnum typeFichier,
            String cheminFichier,
            Long taille,
            TypeDocumentPieceJointeEnum typeDocument,
            LocalDateTime uploadedAt
    ) {
        this.id = id;
        this.courrierId = courrierId;
        this.numeroOrdre = numeroOrdre;
        this.nomFichier = nomFichier;
        this.typeFichier = typeFichier;
        this.cheminFichier = cheminFichier;
        this.taille = taille;
        this.typeDocument = typeDocument;
        this.uploadedAt = uploadedAt;
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

    public String getNomFichier() {
        return nomFichier;
    }

    public TypeFichierEnum getTypeFichier() {
        return typeFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public Long getTaille() {
        return taille;
    }

    public TypeDocumentPieceJointeEnum getTypeDocument() {
        return typeDocument;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}