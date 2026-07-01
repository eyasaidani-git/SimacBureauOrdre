package com.simac.simacordre.entities;

import com.simac.simacordre.enums.TypeDocumentPieceJointeEnum;
import com.simac.simacordre.enums.TypeFichierEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "piece_jointe")
public class PieceJointe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courrier_id", nullable = false)
    private Courrier courrier;

    @Column(name = "nom_fichier", nullable = false, length = 255)
    private String nomFichier;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type_fichier", nullable = false, columnDefinition = "type_fichier_enum")
    private TypeFichierEnum typeFichier;

    @Column(name = "chemin_fichier", nullable = false, length = 500)
    private String cheminFichier;

    @Column(nullable = false)
    private Long taille;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @Column(name = "reference_document", length = 100)
    private String referenceDocument;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type_document", nullable = false, columnDefinition = "type_document_piece_jointe_enum")
    private TypeDocumentPieceJointeEnum typeDocument = TypeDocumentPieceJointeEnum.AUTRE;

    public PieceJointe() {
    }

    public Long getId() {
        return id;
    }

    public Courrier getCourrier() {
        return courrier;
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

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public TypeDocumentPieceJointeEnum getTypeDocument() {
        return typeDocument;
    }

    public String getReferenceDocument() {return referenceDocument;}

    public String getObservation() {return observation;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourrier(Courrier courrier) {
        this.courrier = courrier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public void setTypeFichier(TypeFichierEnum typeFichier) {
        this.typeFichier = typeFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public void setTypeDocument(TypeDocumentPieceJointeEnum typeDocument) {
        this.typeDocument = typeDocument;
    }

    public void setReferenceDocument(String referenceDocument) {this.referenceDocument = referenceDocument;}

    public void setObservation(String observation) {this.observation = observation;}
}