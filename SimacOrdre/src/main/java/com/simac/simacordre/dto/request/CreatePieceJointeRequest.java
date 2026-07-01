package com.simac.simacordre.dto.request;

import com.simac.simacordre.enums.TypeDocumentPieceJointeEnum;
import com.simac.simacordre.enums.TypeFichierEnum;

public class CreatePieceJointeRequest {

    private Long courrierId;
    private String nomFichier;
    private TypeFichierEnum typeFichier;
    private String cheminFichier;
    private Long taille;
    private String referenceDocument;
    private String observation;
    private TypeDocumentPieceJointeEnum typeDocument;

    public CreatePieceJointeRequest() {
    }

    public Long getCourrierId() {
        return courrierId;
    }

    public void setCourrierId(Long courrierId) {
        this.courrierId = courrierId;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public TypeFichierEnum getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(TypeFichierEnum typeFichier) {
        this.typeFichier = typeFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public TypeDocumentPieceJointeEnum getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocumentPieceJointeEnum typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getReferenceDocument() {return referenceDocument;}

    public void setReferenceDocument(String referenceDocument) {this.referenceDocument = referenceDocument;}

    public String getObservation() {return observation;}

    public void setObservation(String observation) {this.observation = observation;}
}