package com.simac.simacordre.dto.request;

public class ResendEmailVerificationCodeRequest {

    private String email;

    public ResendEmailVerificationCodeRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}