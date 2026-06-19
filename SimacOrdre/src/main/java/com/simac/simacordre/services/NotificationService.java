package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.CreateNotificationRequest;
import com.simac.simacordre.dto.response.NotificationResponse;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.Notification;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.NotificationRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final CourrierRepository courrierRepository;
    private final UtilisateurRepository utilisateurRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            CourrierRepository courrierRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.notificationRepository = notificationRepository;
        this.courrierRepository = courrierRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<NotificationResponse> getNotificationsByUtilisateur(Long utilisateurId) {
        return notificationRepository.findByUtilisateurIdOrderByCreatedAtDesc(utilisateurId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<NotificationResponse> getNotificationsNonLues(Long utilisateurId) {
        return notificationRepository.findByUtilisateurIdAndLueFalseOrderByCreatedAtDesc(utilisateurId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public NotificationResponse creerNotification(CreateNotificationRequest request) {
        if (request.getUtilisateurId() == null) {
            throw new RuntimeException("L'utilisateur est obligatoire.");
        }

        if (request.getTitre() == null || request.getTitre().isBlank()) {
            throw new RuntimeException("Le titre est obligatoire.");
        }

        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new RuntimeException("Le message est obligatoire.");
        }

        if (request.getType() == null) {
            throw new RuntimeException("Le type de notification est obligatoire.");
        }

        Utilisateur utilisateur = utilisateurRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));

        Courrier courrier = null;
        if (request.getCourrierId() != null) {
            courrier = courrierRepository.findById(request.getCourrierId())
                    .orElseThrow(() -> new RuntimeException("Courrier introuvable."));
        }

        Notification notification = new Notification();
        notification.setUtilisateur(utilisateur);
        notification.setCourrier(courrier);
        notification.setTitre(request.getTitre());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setLue(false);
        notification.setCreatedAt(LocalDateTime.now());

        return toResponse(notificationRepository.save(notification));
    }

    @Transactional
    public NotificationResponse marquerCommeLue(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification introuvable."));

        notification.setLue(true);

        return toResponse(notificationRepository.save(notification));
    }

    private NotificationResponse toResponse(Notification notification) {
        String utilisateurNomComplet = null;

        if (notification.getUtilisateur() != null) {
            utilisateurNomComplet = notification.getUtilisateur().getPrenom() + " " + notification.getUtilisateur().getNom();
        }

        return new NotificationResponse(
                notification.getId(),
                notification.getCourrier() != null ? notification.getCourrier().getId() : null,
                notification.getCourrier() != null ? notification.getCourrier().getNumeroOrdre() : null,
                notification.getUtilisateur() != null ? notification.getUtilisateur().getId() : null,
                utilisateurNomComplet,
                notification.getTitre(),
                notification.getMessage(),
                notification.getType(),
                notification.getLue(),
                notification.getCreatedAt()
        );
    }
}