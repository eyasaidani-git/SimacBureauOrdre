package com.simac.simacordre.entities;

import com.simac.simacordre.enums.CategorieReclamationEnum;
import com.simac.simacordre.enums.StatutReclamationEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reclamation")
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courrier_id")
    private Courrier courrier;

    @Column(name = "sujet", nullable = false, length = 255)
    private String sujet;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "categorie", nullable = false, columnDefinition = "categorie_reclamation_enum")
    private CategorieReclamationEnum categorie;

    @Column(name = "nombre_spams")
    private Integer nombreSpams;

    @Column(name = "periode_debut")
    private LocalDate periodeDebut;

    @Column(name = "periode_fin")
    private LocalDate periodeFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 50)
    private StatutReclamationEnum statut = StatutReclamationEnum.OUVERTE;

    @Column(name = "reponse_admin", columnDefinition = "TEXT")
    private String reponse;

    @ManyToOne
    @JoinColumn(name = "traitee_par_id")
    private Utilisateur traiteePar;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "createur_id", nullable = false)
    private Utilisateur createur;

    @Column(name = "responsable_concerne_id")
    private Long responsableConcerneId;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Reclamation() {
    }

    public Long getId() {
        return id;
    }

    public Courrier getCourrier() {
        return courrier;
    }

    public String getSujet() {
        return sujet;
    }

    public CategorieReclamationEnum getCategorie() {
        return categorie;
    }

    public Integer getNombreSpams() {
        return nombreSpams;
    }

    public LocalDate getPeriodeDebut() {
        return periodeDebut;
    }

    public LocalDate getPeriodeFin() {
        return periodeFin;
    }

    public StatutReclamationEnum getStatut() {
        return statut;
    }

    public String getReponse() {
        return reponse;
    }

    public Utilisateur getTraiteePar() {
        return traiteePar;
    }

    public String getDescription() {
        return description;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public Long getResponsableConcerneId() {
        return responsableConcerneId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourrier(Courrier courrier) {
        this.courrier = courrier;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public void setCategorie(CategorieReclamationEnum categorie) {
        this.categorie = categorie;
    }

    public void setNombreSpams(Integer nombreSpams) {
        this.nombreSpams = nombreSpams;
    }

    public void setPeriodeDebut(LocalDate periodeDebut) {
        this.periodeDebut = periodeDebut;
    }

    public void setPeriodeFin(LocalDate periodeFin) {
        this.periodeFin = periodeFin;
    }

    public void setStatut(StatutReclamationEnum statut) {
        this.statut = statut;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setTraiteePar(Utilisateur traiteePar) {
        this.traiteePar = traiteePar;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public void setResponsableConcerneId(Long responsableConcerneId) {
        this.responsableConcerneId = responsableConcerneId;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}