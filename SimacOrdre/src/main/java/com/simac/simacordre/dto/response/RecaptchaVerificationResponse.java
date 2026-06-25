package com.simac.simacordre.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaVerificationResponse {

    private Boolean success;

    private Double score;

    private String action;

    private String hostname;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public RecaptchaVerificationResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public Double getScore() {
        return score;
    }

    public String getAction() {
        return action;
    }

    public String getHostname() {
        return hostname;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setChallengeTs(String challengeTs) {
        this.challengeTs = challengeTs;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}