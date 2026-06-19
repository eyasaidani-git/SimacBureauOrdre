package com.simac.simacordre.entities;

import com.simac.simacordre.enums.StatutCourrierEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_statut")
public class HistoriqueStatut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courrier_id", nullable = false)
    private Courrier courrier;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "ancien_statut", nullable = false, columnDefinition = "statut_courrier_enum")
    private StatutCourrierEnum ancienStatut;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "nouveau_statut", nullable = false, columnDefinition = "statut_courrier_enum")
    private StatutCourrierEnum nouveauStatut;

    @ManyToOne
    @JoinColumn(name = "modifie_par_id", nullable = false)
    private Utilisateur modifiePar;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public HistoriqueStatut() {
    }

    public Long getId() {
        return id;
    }

    public Courrier getCourrier() {
        return courrier;
    }

    public StatutCourrierEnum getAncienStatut() {
        return ancienStatut;
    }

    public StatutCourrierEnum getNouveauStatut() {
        return nouveauStatut;
    }

    public Utilisateur getModifiePar() {
        return modifiePar;
    }

    public String getCommentaire() {
        return commentaire;
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

    public void setAncienStatut(StatutCourrierEnum ancienStatut) {
        this.ancienStatut = ancienStatut;
    }

    public void setNouveauStatut(StatutCourrierEnum nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    public void setModifiePar(Utilisateur modifiePar) {
        this.modifiePar = modifiePar;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}