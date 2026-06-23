package com.simac.simacordre.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_spam")
public class EmailSpam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expediteur", length = 255)
    private String expediteur;

    @Column(name = "objet", length = 255)
    private String objet;

    @Column(name = "contenu", columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "confirme_spam", nullable = false)
    private Boolean confirmeSpam = true;

    @Column(name = "date_reception", nullable = false)
    private LocalDateTime dateReception;

    @ManyToOne
    @JoinColumn(name = "traite_par_id")
    private Utilisateur traitePar;

    @ManyToOne
    @JoinColumn(name = "responsable_concerne_id")
    private Utilisateur responsableConcerne;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public EmailSpam() {
    }

    public Long getId() {
        return id;
    }

    public String getExpediteur() {
        return expediteur;
    }

    public String getObjet() {
        return objet;
    }

    public String getContenu() {
        return contenu;
    }

    public Boolean getConfirmeSpam() {
        return confirmeSpam;
    }

    public LocalDateTime getDateReception() {
        return dateReception;
    }

    public Utilisateur getTraitePar() {
        return traitePar;
    }

    public Utilisateur getResponsableConcerne() {
        return responsableConcerne;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setConfirmeSpam(Boolean confirmeSpam) {
        this.confirmeSpam = confirmeSpam;
    }

    public void setDateReception(LocalDateTime dateReception) {
        this.dateReception = dateReception;
    }

    public void setTraitePar(Utilisateur traitePar) {
        this.traitePar = traitePar;
    }

    public void setResponsableConcerne(Utilisateur responsableConcerne) {
        this.responsableConcerne = responsableConcerne;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}