package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.LoginRequest;
import com.simac.simacordre.dto.response.LoginResponse;
import com.simac.simacordre.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
}