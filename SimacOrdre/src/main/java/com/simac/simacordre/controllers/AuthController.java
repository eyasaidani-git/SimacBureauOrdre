package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.ChangePasswordFirstLoginRequest;
import com.simac.simacordre.dto.request.LoginRequest;
import com.simac.simacordre.dto.request.ResendEmailVerificationCodeRequest;
import com.simac.simacordre.dto.request.VerifyEmailRequest;
import com.simac.simacordre.dto.response.LoginResponse;
import com.simac.simacordre.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "API pour la connexion des utilisateurs")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/send-verification-code")
    @Operation(summary = "Envoyer un code de vérification email")
    public Map<String, String> envoyerCodeVerificationEmail(
            @RequestBody ResendEmailVerificationCodeRequest request
    ) {
        String message = authService.envoyerCodeVerificationEmail(request);
        return Map.of("message", message);
    }

    @PostMapping("/verify-email")
    @Operation(summary = "Vérifier l'adresse email avec un code")
    public Map<String, String> verifierEmail(@RequestBody VerifyEmailRequest request) {
        String message = authService.verifierEmail(request);
        return Map.of("message", message);
    }

    @PostMapping("/change-password-first-login")
    @Operation(summary = "Changer le mot de passe lors de la première connexion")
    public Map<String, String> changerMotDePassePremiereConnexion(
            @RequestBody ChangePasswordFirstLoginRequest request
    ) {
        String message = authService.changerMotDePassePremiereConnexion(request);
        return Map.of("message", message);
    }
}