package com.simac.simacordre.dto.request;

public class ChangePasswordFirstLoginRequest {

    private String email;
    private String ancienMotDePasse;
    private String nouveauMotDePasse;
    private String confirmationMotDePasse;

    public ChangePasswordFirstLoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public String getConfirmationMotDePasse() {
        return confirmationMotDePasse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public void setConfirmationMotDePasse(String confirmationMotDePasse) {
        this.confirmationMotDePasse = confirmationMotDePasse;
    }
}