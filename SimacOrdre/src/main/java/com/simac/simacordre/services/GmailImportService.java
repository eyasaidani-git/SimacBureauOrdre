package com.simac.simacordre.services;

import com.simac.simacordre.dto.response.GmailImportResultResponse;
import com.simac.simacordre.dto.response.GmailMessagePreviewResponse;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.EmailImportLog;
import com.simac.simacordre.entities.EmailSpam;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.enums.PrioriteEnum;
import com.simac.simacordre.enums.StatutCourrierEnum;
import com.simac.simacordre.enums.TypeCourrierEnum;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.EmailImportLogRepository;
import com.simac.simacordre.repositories.EmailSpamRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GmailImportService {

    private final GmailReaderService gmailReaderService;
    private final EmailImportLogRepository emailImportLogRepository;
    private final CourrierRepository courrierRepository;
    private final EmailSpamRepository emailSpamRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final NumeroOrdreService numeroOrdreService;

    @Value("${app.gmail.import.createur-email}")
    private String createurEmail;

    @Value("${app.gmail.import.destination:SIMAC}")
    private String destinationParDefaut;

    @Value("${app.gmail.import.date-limite-days:7}")
    private int dateLimiteDays;

    public GmailImportService(
            GmailReaderService gmailReaderService,
            EmailImportLogRepository emailImportLogRepository,
            CourrierRepository courrierRepository,
            EmailSpamRepository emailSpamRepository,
            UtilisateurRepository utilisateurRepository,
            NumeroOrdreService numeroOrdreService
    ) {
        this.gmailReaderService = gmailReaderService;
        this.emailImportLogRepository = emailImportLogRepository;
        this.courrierRepository = courrierRepository;
        this.emailSpamRepository = emailSpamRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.numeroOrdreService = numeroOrdreService;
    }

    @Transactional
    public GmailImportResultResponse importerMaintenant() {
        List<GmailMessagePreviewResponse> emails = gmailReaderService.lireDerniersEmailsPourPreview();

        int emailsLus = emails.size();
        int courriersImportes = 0;
        int spamsImportes = 0;
        int doublonsIgnores = 0;

        Utilisateur createur = utilisateurRepository.findByEmail(createurEmail)
                .orElseThrow(() -> new RuntimeException(
                        "Utilisateur créateur introuvable avec email : " + createurEmail
                ));

        for (GmailMessagePreviewResponse email : emails) {
            if (email.getMessageId() == null || email.getMessageId().isBlank()) {
                continue;
            }

            if (emailImportLogRepository.existsByMessageId(email.getMessageId())) {
                doublonsIgnores++;
                continue;
            }

            if (Boolean.TRUE.equals(email.getSpamDossier())) {
                importerCommeSpam(email);
                enregistrerLog(email, "EMAIL_SPAM");
                spamsImportes++;
            } else {
                importerCommeCourrier(email, createur);
                enregistrerLog(email, "COURRIER");
                courriersImportes++;
            }
        }

        return new GmailImportResultResponse(
                emailsLus,
                courriersImportes,
                spamsImportes,
                doublonsIgnores
        );
    }

    private void importerCommeCourrier(
            GmailMessagePreviewResponse email,
            Utilisateur createur
    ) {
        LocalDate dateReception = extraireDateReception(email);

        NumeroOrdreService.ResultatNumeroOrdre resultatNumeroOrdre =
                numeroOrdreService.genererNumeroOrdre(dateReception);

        Courrier courrier = new Courrier();

        courrier.setNumeroOrdre(resultatNumeroOrdre.getNumeroOrdre());
        courrier.setNumeroOrdreSeq(resultatNumeroOrdre.getNumeroOrdreSeq());

        courrier.setObjet(valeurOuDefaut(email.getSujet(), "Email administratif sans objet"));
        courrier.setExpediteur(valeurOuDefaut(email.getExpediteur(), "Expéditeur inconnu"));
        courrier.setDestination(destinationParDefaut);

        courrier.setType(TypeCourrierEnum.EMAIL_ADMINISTRATIF);
        courrier.setStatut(StatutCourrierEnum.RECU);
        courrier.setPriorite(PrioriteEnum.NORMALE);

        courrier.setDateReception(dateReception);
        courrier.setDateLimite(dateReception.plusDays(dateLimiteDays));

        courrier.setDescription(valeurOuDefaut(
                email.getApercuContenu(),
                "Email importé automatiquement depuis Gmail."
        ));

        courrier.setArchive(false);
        courrier.setCreateur(createur);
        courrier.setDepartement(createur.getDepartement());
        courrier.setCreatedAt(LocalDateTime.now());

        courrierRepository.save(courrier);
    }

    private void importerCommeSpam(GmailMessagePreviewResponse email) {
        EmailSpam emailSpam = new EmailSpam();

        emailSpam.setExpediteur(valeurOuDefaut(email.getExpediteur(), "Expéditeur inconnu"));
        emailSpam.setObjet(valeurOuDefaut(email.getSujet(), "Spam sans objet"));
        emailSpam.setContenu(valeurOuDefaut(
                email.getApercuContenu(),
                "Email spam importé automatiquement depuis Gmail."
        ));

        emailSpam.setConfirmeSpam(true);
        emailSpam.setDateReception(extraireDateMessage(email));
        emailSpam.setCreatedAt(LocalDateTime.now());

        emailSpamRepository.save(emailSpam);
    }

    private void enregistrerLog(GmailMessagePreviewResponse email, String importeComme) {
        EmailImportLog log = new EmailImportLog();

        log.setMessageId(email.getMessageId());
        log.setDossierGmail(valeurOuDefaut(email.getDossierGmail(), "UNKNOWN"));
        log.setSujet(email.getSujet());
        log.setExpediteur(email.getExpediteur());
        log.setDateMessage(extraireDateMessage(email));
        log.setImporteComme(importeComme);
        log.setCreatedAt(LocalDateTime.now());

        emailImportLogRepository.save(log);
    }

    private LocalDate extraireDateReception(GmailMessagePreviewResponse email) {
        if (email.getDateMessage() == null) {
            return LocalDate.now();
        }

        return email.getDateMessage().toLocalDate();
    }

    private LocalDateTime extraireDateMessage(GmailMessagePreviewResponse email) {
        if (email.getDateMessage() == null) {
            return LocalDateTime.now();
        }

        return email.getDateMessage();
    }

    private String valeurOuDefaut(String valeur, String defaut) {
        if (valeur == null || valeur.isBlank()) {
            return defaut;
        }

        return valeur;
    }
}