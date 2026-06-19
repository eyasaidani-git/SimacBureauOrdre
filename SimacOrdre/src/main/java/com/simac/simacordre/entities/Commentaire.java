package com.simac.simacordre.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commentaire")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courrier_id", nullable = false)
    private Courrier courrier;

    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private Utilisateur auteur;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Commentaire() {
    }

    public Long getId() {
        return id;
    }

    public Courrier getCourrier() {
        return courrier;
    }

    public Utilisateur getAuteur() {
        return auteur;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourrier(Courrier courrier) {
        this.courrier = courrier;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}