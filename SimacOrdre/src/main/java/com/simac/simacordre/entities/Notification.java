package com.simac.simacordre.entities;

import com.simac.simacordre.enums.TypeNotificationEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courrier_id")
    private Courrier courrier;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "type_notification_enum")
    private TypeNotificationEnum type;

    @Column(nullable = false)
    private Boolean lue = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public Courrier getCourrier() {
        return courrier;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public String getTitre() {
        return titre;
    }

    public String getMessage() {
        return message;
    }

    public TypeNotificationEnum getType() {
        return type;
    }

    public Boolean getLue() {
        return lue;
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

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(TypeNotificationEnum type) {
        this.type = type;
    }

    public void setLue(Boolean lue) {
        this.lue = lue;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}