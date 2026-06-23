package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.CreateEmailSpamRequest;
import com.simac.simacordre.dto.request.TraiterEmailSpamRequest;
import com.simac.simacordre.dto.response.EmailSpamResponse;
import com.simac.simacordre.services.EmailSpamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emails-spam")
@Tag(name = "Emails spam", description = "API pour la gestion des emails détectés comme spam")
public class EmailSpamController {

    private final EmailSpamService emailSpamService;

    public EmailSpamController(EmailSpamService emailSpamService) {
        this.emailSpamService = emailSpamService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les emails spam")
    public List<EmailSpamResponse> getAllEmailsSpam() {
        return emailSpamService.getAllEmailsSpam();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un email spam par ID")
    public EmailSpamResponse getEmailSpamById(@PathVariable Long id) {
        return emailSpamService.getEmailSpamById(id);
    }

    @GetMapping("/confirmes")
    @Operation(summary = "Lister les emails confirmés comme spam")
    public List<EmailSpamResponse> getEmailsConfirmesSpam() {
        return emailSpamService.getEmailsConfirmesSpam();
    }

    @GetMapping("/non-spam")
    @Operation(summary = "Lister les emails marqués comme non spam")
    public List<EmailSpamResponse> getEmailsNonSpam() {
        return emailSpamService.getEmailsNonSpam();
    }

    @GetMapping("/responsable-concerne/{responsableConcerneId}")
    @Operation(summary = "Lister les emails spam par responsable concerné")
    public List<EmailSpamResponse> getEmailsSpamByResponsableConcerne(@PathVariable Long responsableConcerneId) {
        return emailSpamService.getEmailsSpamByResponsableConcerne(responsableConcerneId);
    }

    @GetMapping("/traite-par/{traiteParId}")
    @Operation(summary = "Lister les emails spam traités par un utilisateur")
    public List<EmailSpamResponse> getEmailsSpamByTraitePar(@PathVariable Long traiteParId) {
        return emailSpamService.getEmailsSpamByTraitePar(traiteParId);
    }

    @GetMapping("/count/semaine")
    @Operation(summary = "Compter les spams confirmés cette semaine")
    public long countSpamsConfirmesCetteSemaine() {
        return emailSpamService.countSpamsConfirmesCetteSemaine();
    }

    @PostMapping
    @Operation(summary = "Créer un email spam")
    public EmailSpamResponse creerEmailSpam(@RequestBody CreateEmailSpamRequest request) {
        return emailSpamService.creerEmailSpam(request);
    }

    @PutMapping("/{id}/confirmer")
    @Operation(summary = "Confirmer qu'un email est un spam")
    public EmailSpamResponse confirmerCommeSpam(
            @PathVariable Long id,
            @RequestBody TraiterEmailSpamRequest request
    ) {
        return emailSpamService.confirmerCommeSpam(id, request);
    }

    @PutMapping("/{id}/non-spam")
    @Operation(summary = "Marquer un email comme non spam")
    public EmailSpamResponse marquerCommeNonSpam(
            @PathVariable Long id,
            @RequestBody TraiterEmailSpamRequest request
    ) {
        return emailSpamService.marquerCommeNonSpam(id, request);
    }
}