package com.simac.simacordre.dto.request;

public class VerifyResetPasswordCodeRequest {

    private String email;
    private String code;

    public VerifyResetPasswordCodeRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }
}