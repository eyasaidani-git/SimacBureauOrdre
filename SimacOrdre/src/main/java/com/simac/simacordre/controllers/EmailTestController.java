package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.EmailRequest;
import com.simac.simacordre.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/emails")
@Tag(name = "Emails", description = "API de test pour l'envoi d'emails")
public class EmailTestController {

    private final EmailService emailService;

    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/test")
    @Operation(summary = "Envoyer un email de test")
    public Map<String, String> envoyerEmailTest(@RequestBody EmailRequest request) {
        emailService.envoyerEmailSimple(
                request.getTo(),
                request.getSubject(),
                request.getMessage()
        );

        return Map.of("message", "Email envoyé avec succès.");
    }
}