package com.simac.simacordre.services;

import com.simac.simacordre.dto.response.RecaptchaVerificationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${app.recaptcha.enabled:false}")
    private boolean recaptchaEnabled;

    @Value("${app.recaptcha.secret:}")
    private String recaptchaSecret;

    @Value("${app.recaptcha.minimum-score:0.5}")
    private double minimumScore;

    private final RestTemplate restTemplate = new RestTemplate();

    public void verifierToken(String recaptchaToken, String expectedAction) {
        if (!recaptchaEnabled) {
            return;
        }

        if (recaptchaSecret == null || recaptchaSecret.isBlank()) {
            throw new RuntimeException("La clé secrète reCAPTCHA n'est pas configurée.");
        }

        if (recaptchaToken == null || recaptchaToken.isBlank()) {
            throw new RuntimeException("Le token reCAPTCHA est obligatoire.");
        }

        RecaptchaVerificationResponse response = appelerGoogleRecaptcha(recaptchaToken);

        if (!Boolean.TRUE.equals(response.getSuccess())) {
            throw new RuntimeException("Vérification reCAPTCHA échouée.");
        }

        if (response.getScore() != null && response.getScore() < minimumScore) {
            throw new RuntimeException("Score reCAPTCHA insuffisant.");
        }

        if (expectedAction != null
                && !expectedAction.isBlank()
                && response.getAction() != null
                && !expectedAction.equals(response.getAction())) {
            throw new RuntimeException("Action reCAPTCHA invalide.");
        }
    }

    private RecaptchaVerificationResponse appelerGoogleRecaptcha(String recaptchaToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", recaptchaSecret);
        body.add("response", recaptchaToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<RecaptchaVerificationResponse> response = restTemplate.postForEntity(
                RECAPTCHA_VERIFY_URL,
                request,
                RecaptchaVerificationResponse.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("Réponse reCAPTCHA vide.");
        }

        return response.getBody();
    }
}