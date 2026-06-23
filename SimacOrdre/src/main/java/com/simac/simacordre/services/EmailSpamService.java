package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.CreateEmailSpamRequest;
import com.simac.simacordre.dto.request.TraiterEmailSpamRequest;
import com.simac.simacordre.dto.response.EmailSpamResponse;
import com.simac.simacordre.entities.EmailSpam;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.repositories.EmailSpamRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailSpamService {

    private final EmailSpamRepository emailSpamRepository;
    private final UtilisateurRepository utilisateurRepository;

    public EmailSpamService(
            EmailSpamRepository emailSpamRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.emailSpamRepository = emailSpamRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<EmailSpamResponse> getAllEmailsSpam() {
        return emailSpamRepository.findAllByOrderByDateReceptionDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EmailSpamResponse getEmailSpamById(Long id) {
        EmailSpam emailSpam = emailSpamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Email spam introuvable avec id : " + id));

        return toResponse(emailSpam);
    }

    public List<EmailSpamResponse> getEmailsConfirmesSpam() {
        return emailSpamRepository.findByConfirmeSpamTrueOrderByDateReceptionDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmailSpamResponse> getEmailsNonSpam() {
        return emailSpamRepository.findByConfirmeSpamFalseOrderByDateReceptionDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmailSpamResponse> getEmailsSpamByResponsableConcerne(Long responsableConcerneId) {
        return emailSpamRepository.findByResponsableConcerneIdOrderByDateReceptionDesc(responsableConcerneId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmailSpamResponse> getEmailsSpamByTraitePar(Long traiteParId) {
        return emailSpamRepository.findByTraiteParIdOrderByDateReceptionDesc(traiteParId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public long countSpamsConfirmesCetteSemaine() {
        LocalDate today = LocalDate.now();
        LocalDate debutSemaine = today.with(DayOfWeek.MONDAY);
        LocalDate finSemaine = today.with(DayOfWeek.SUNDAY);

        LocalDateTime debut = debutSemaine.atStartOfDay();
        LocalDateTime fin = finSemaine.atTime(23, 59, 59);

        return emailSpamRepository.countByConfirmeSpamTrueAndCreatedAtBetween(debut, fin);    }

    @Transactional
    public EmailSpamResponse creerEmailSpam(CreateEmailSpamRequest request) {
        if (request.getDateReception() == null) {
            throw new RuntimeException("La date de réception est obligatoire.");
        }

        EmailSpam emailSpam = new EmailSpam();
        emailSpam.setExpediteur(request.getExpediteur());
        emailSpam.setObjet(request.getObjet());
        emailSpam.setContenu(request.getContenu());
        emailSpam.setConfirmeSpam(request.getConfirmeSpam() != null ? request.getConfirmeSpam() : true);
        emailSpam.setDateReception(request.getDateReception());
        emailSpam.setCreatedAt(LocalDateTime.now());

        if (request.getTraiteParId() != null) {
            Utilisateur traitePar = utilisateurRepository.findById(request.getTraiteParId())
                    .orElseThrow(() -> new RuntimeException(
                            "Utilisateur traitant introuvable avec id : " + request.getTraiteParId()
                    ));

            emailSpam.setTraitePar(traitePar);
        }

        if (request.getResponsableConcerneId() != null) {
            Utilisateur responsableConcerne = utilisateurRepository.findById(request.getResponsableConcerneId())
                    .orElseThrow(() -> new RuntimeException(
                            "Responsable concerné introuvable avec id : " + request.getResponsableConcerneId()
                    ));

            emailSpam.setResponsableConcerne(responsableConcerne);
        }

        EmailSpam saved = emailSpamRepository.save(emailSpam);

        return toResponse(saved);
    }

    @Transactional
    public EmailSpamResponse confirmerCommeSpam(Long id, TraiterEmailSpamRequest request) {
        if (request.getTraiteParId() == null) {
            throw new RuntimeException("L'utilisateur qui traite l'email est obligatoire.");
        }

        EmailSpam emailSpam = emailSpamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Email spam introuvable avec id : " + id));

        Utilisateur traitePar = utilisateurRepository.findById(request.getTraiteParId())
                .orElseThrow(() -> new RuntimeException(
                        "Utilisateur traitant introuvable avec id : " + request.getTraiteParId()
                ));

        emailSpam.setConfirmeSpam(true);
        emailSpam.setTraitePar(traitePar);

        EmailSpam saved = emailSpamRepository.save(emailSpam);

        return toResponse(saved);
    }

    @Transactional
    public EmailSpamResponse marquerCommeNonSpam(Long id, TraiterEmailSpamRequest request) {
        if (request.getTraiteParId() == null) {
            throw new RuntimeException("L'utilisateur qui traite l'email est obligatoire.");
        }

        EmailSpam emailSpam = emailSpamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Email spam introuvable avec id : " + id));

        Utilisateur traitePar = utilisateurRepository.findById(request.getTraiteParId())
                .orElseThrow(() -> new RuntimeException(
                        "Utilisateur traitant introuvable avec id : " + request.getTraiteParId()
                ));

        emailSpam.setConfirmeSpam(false);
        emailSpam.setTraitePar(traitePar);

        EmailSpam saved = emailSpamRepository.save(emailSpam);

        return toResponse(saved);
    }

    private EmailSpamResponse toResponse(EmailSpam emailSpam) {
        String traiteParNomComplet = null;

        if (emailSpam.getTraitePar() != null) {
            traiteParNomComplet = emailSpam.getTraitePar().getPrenom()
                    + " "
                    + emailSpam.getTraitePar().getNom();
        }

        String responsableConcerneNomComplet = null;

        if (emailSpam.getResponsableConcerne() != null) {
            responsableConcerneNomComplet = emailSpam.getResponsableConcerne().getPrenom()
                    + " "
                    + emailSpam.getResponsableConcerne().getNom();
        }

        return new EmailSpamResponse(
                emailSpam.getId(),
                emailSpam.getExpediteur(),
                emailSpam.getObjet(),
                emailSpam.getContenu(),
                emailSpam.getConfirmeSpam(),
                emailSpam.getDateReception(),
                emailSpam.getTraitePar() != null ? emailSpam.getTraitePar().getId() : null,
                traiteParNomComplet,
                emailSpam.getResponsableConcerne() != null ? emailSpam.getResponsableConcerne().getId() : null,
                responsableConcerneNomComplet,
                emailSpam.getCreatedAt()
        );
    }
}