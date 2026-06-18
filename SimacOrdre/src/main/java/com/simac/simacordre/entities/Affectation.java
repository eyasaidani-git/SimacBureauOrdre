package com.simac.simacordre.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "affectation")
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courrier_id", nullable = false)
    private Courrier courrier;

    @ManyToOne
    @JoinColumn(name = "affecte_par_id", nullable = false)
    private Utilisateur affectePar;

    @ManyToOne
    @JoinColumn(name = "affecte_a_utilisateur_id")
    private Utilisateur affecteAUtilisateur;

    @ManyToOne
    @JoinColumn(name = "affecte_a_departement_id")
    private Departement affecteADepartement;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Affectation() {
    }

    public Long getId() {
        return id;
    }

    public Courrier getCourrier() {
        return courrier;
    }

    public Utilisateur getAffectePar() {
        return affectePar;
    }

    public Utilisateur getAffecteAUtilisateur() {
        return affecteAUtilisateur;
    }

    public Departement getAffecteADepartement() {
        return affecteADepartement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Boolean getActive() {
        return active;
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

    public void setAffectePar(Utilisateur affectePar) {
        this.affectePar = affectePar;
    }

    public void setAffecteAUtilisateur(Utilisateur affecteAUtilisateur) {
        this.affecteAUtilisateur = affecteAUtilisateur;
    }

    public void setAffecteADepartement(Departement affecteADepartement) {
        this.affecteADepartement = affecteADepartement;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}