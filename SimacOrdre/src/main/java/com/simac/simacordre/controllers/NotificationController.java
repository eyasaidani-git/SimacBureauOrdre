package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.CreateNotificationRequest;
import com.simac.simacordre.dto.response.NotificationResponse;
import com.simac.simacordre.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "API pour la gestion des notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Lister toutes les notifications")
    public List<NotificationResponse> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR', 'EMPLOYE')")
    @GetMapping("/utilisateur/{utilisateurId}")
    @Operation(summary = "Lister les notifications d'un utilisateur")
    public List<NotificationResponse> getNotificationsByUtilisateur(@PathVariable Long utilisateurId) {
        return notificationService.getNotificationsByUtilisateur(utilisateurId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR', 'EMPLOYE')")
    @GetMapping("/utilisateur/{utilisateurId}/non-lues")
    @Operation(summary = "Lister les notifications non lues d'un utilisateur")
    public List<NotificationResponse> getNotificationsNonLues(@PathVariable Long utilisateurId) {
        return notificationService.getNotificationsNonLues(utilisateurId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE')")
    @PostMapping
    @Operation(summary = "Créer une notification")
    public NotificationResponse creerNotification(@RequestBody CreateNotificationRequest request) {
        return notificationService.creerNotification(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR', 'EMPLOYE')")
    @PutMapping("/{notificationId}/lire")
    @Operation(summary = "Marquer une notification comme lue")
    public NotificationResponse marquerCommeLue(@PathVariable Long notificationId) {
        return notificationService.marquerCommeLue(notificationId);
    }
}