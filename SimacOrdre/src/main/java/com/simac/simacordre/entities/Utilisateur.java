package com.simac.simacordre.entities;

import com.simac.simacordre.enums.RoleEnum;
import com.simac.simacordre.enums.StatutUtilisateurEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @Column(length = 20)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "role_enum")
    private RoleEnum role;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "statut_utilisateur_enum")
    private StatutUtilisateurEnum statut;

    @Column(name = "premiere_connexion", nullable = false)
    private Boolean premiereConnexion;

    @Column(name = "email_verifie", nullable = false)
    private Boolean emailVerifie = false;

    @Column(name = "code_verification_email", length = 10)
    private String codeVerificationEmail;

    @Column(name = "code_verification_expire_at")
    private LocalDateTime codeVerificationExpireAt;

    @Column(name = "code_reset_password", length = 10)
    private String codeResetPassword;

    @Column(name = "code_reset_password_expire_at")
    private LocalDateTime codeResetPasswordExpireAt;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Utilisateur responsable;

    @Column(name = "photo_profil", length = 500)
    private String photoProfil;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Utilisateur() {
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public RoleEnum getRole() {
        return role;
    }

    public StatutUtilisateurEnum getStatut() {
        return statut;
    }

    public Boolean getPremiereConnexion() {
        return premiereConnexion;
    }

    public Boolean getEmailVerifie() {
        return emailVerifie;
    }

    public String getCodeVerificationEmail() {
        return codeVerificationEmail;
    }

    public LocalDateTime getCodeVerificationExpireAt() {
        return codeVerificationExpireAt;
    }

    public Departement getDepartement() {
        return departement;
    }

    public Utilisateur getResponsable() {
        return responsable;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public void setStatut(StatutUtilisateurEnum statut) {
        this.statut = statut;
    }

    public void setPremiereConnexion(Boolean premiereConnexion) {
        this.premiereConnexion = premiereConnexion;
    }

    public void setEmailVerifie(Boolean emailVerifie) {
        this.emailVerifie = emailVerifie;
    }

    public void setCodeVerificationEmail(String codeVerificationEmail) {
        this.codeVerificationEmail = codeVerificationEmail;
    }

    public void setCodeVerificationExpireAt(LocalDateTime codeVerificationExpireAt) {
        this.codeVerificationExpireAt = codeVerificationExpireAt;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCodeResetPassword() {
        return codeResetPassword;
    }

    public LocalDateTime getCodeResetPasswordExpireAt() {
        return codeResetPasswordExpireAt;
    }

    public void setCodeResetPassword(String codeResetPassword) {
        this.codeResetPassword = codeResetPassword;
    }

    public void setCodeResetPasswordExpireAt(LocalDateTime codeResetPasswordExpireAt) {
        this.codeResetPasswordExpireAt = codeResetPasswordExpireAt;
    }
}