package com.simac.simacordre.dto.request;

public class LoginRequest {

    private String email;
    private String motDePasse;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}