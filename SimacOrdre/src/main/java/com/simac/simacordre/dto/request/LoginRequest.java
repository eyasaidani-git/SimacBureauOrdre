package com.simac.simacordre.dto.request;

public class LoginRequest {

    private String email;
    private String motDePasse;
    private String recaptchaToken;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getRecaptchaToken() {
        return recaptchaToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setRecaptchaToken(String recaptchaToken) {
        this.recaptchaToken = recaptchaToken;
    }
}