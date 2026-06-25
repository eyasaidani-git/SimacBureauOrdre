package com.simac.simacordre.dto.request;

public class ForgotPasswordRequest {

    private String email;
    private String recaptchaToken;

    public ForgotPasswordRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getRecaptchaToken() {
        return recaptchaToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRecaptchaToken(String recaptchaToken) {
        this.recaptchaToken = recaptchaToken;
    }
}