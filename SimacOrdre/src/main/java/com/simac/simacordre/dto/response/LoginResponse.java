package com.simac.simacordre.dto.response;

public class LoginResponse {

    private String token;
    private String type;
    private UtilisateurResponse utilisateur;

    public LoginResponse(String token, UtilisateurResponse utilisateur) {
        this.token = token;
        this.type = "Bearer";
        this.utilisateur = utilisateur;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public UtilisateurResponse getUtilisateur() {
        return utilisateur;
    }
}