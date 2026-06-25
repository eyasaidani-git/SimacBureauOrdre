package com.simac.simacordre.dto.request;

public class ResetPasswordRequest {

    private String email;
    private String code;
    private String nouveauMotDePasse;
    private String confirmationMotDePasse;

    public ResetPasswordRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public void setConfirmationMotDePasse(String confirmationMotDePasse) {
        this.confirmationMotDePasse = confirmationMotDePasse;
    }
}