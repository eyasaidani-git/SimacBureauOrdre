package com.simac.simacordre.entities;

import com.simac.simacordre.enums.PrioriteEnum;
import com.simac.simacordre.enums.StatutCourrierEnum;
import com.simac.simacordre.enums.TypeCourrierEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "courrier")
public class Courrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_ordre", nullable = false, unique = true, length = 50)
    private String numeroOrdre;

    @ManyToOne
    @JoinColumn(name = "numero_ordre_seq_id")
    private NumeroOrdreSeq numeroOrdreSeq;

    @Column(nullable = false, length = 500)
    private String objet;

    @Column(nullable = false, length = 255)
    private String expediteur;

    @Column(length = 255)
    private String destination;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "type_courrier_enum")
    private TypeCourrierEnum type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "statut_courrier_enum")
    private StatutCourrierEnum statut = StatutCourrierEnum.RECU;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "priorite_enum")
    private PrioriteEnum priorite = PrioriteEnum.NORMALE;

    @Column(name = "date_reception", nullable = false)
    private LocalDate dateReception;

    @Column(name = "date_limite", nullable = false)
    private LocalDate dateLimite;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean archive = false;

    @ManyToOne
    @JoinColumn(name = "createur_id", nullable = false)
    private Utilisateur createur;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Courrier() {
    }

    public Long getId() {
        return id;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public NumeroOrdreSeq getNumeroOrdreSeq() {
        return numeroOrdreSeq;
    }

    public String getObjet() {
        return objet;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getDestination() {
        return destination;
    }

    public TypeCourrierEnum getType() {
        return type;
    }

    public StatutCourrierEnum getStatut() {
        return statut;
    }

    public PrioriteEnum getPriorite() {
        return priorite;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public LocalDate getDateLimite() {
        return dateLimite;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getArchive() {
        return archive;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public Departement getDepartement() {
        return departement;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public void setNumeroOrdreSeq(NumeroOrdreSeq numeroOrdreSeq) {
        this.numeroOrdreSeq = numeroOrdreSeq;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setType(TypeCourrierEnum type) {
        this.type = type;
    }

    public void setStatut(StatutCourrierEnum statut) {
        this.statut = statut;
    }

    public void setPriorite(PrioriteEnum priorite) {
        this.priorite = priorite;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public void setDateLimite(LocalDate dateLimite) {
        this.dateLimite = dateLimite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}