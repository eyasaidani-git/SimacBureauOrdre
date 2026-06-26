package com.simac.simacordre.services;

import com.simac.simacordre.dto.response.GmailImportResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GmailImportScheduler {

    private static final Logger logger = LoggerFactory.getLogger(GmailImportScheduler.class);

    private final GmailImportService gmailImportService;

    @Value("${app.gmail.import.enabled:false}")
    private boolean gmailImportEnabled;

    public GmailImportScheduler(GmailImportService gmailImportService) {
        this.gmailImportService = gmailImportService;
    }

    @Scheduled(
            fixedDelayString = "${app.gmail.import.fixed-delay-ms:300000}",
            initialDelayString = "${app.gmail.import.initial-delay-ms:30000}"
    )
    public void importerEmailsAutomatiquement() {
        if (!gmailImportEnabled) {
            return;
        }

        try {
            logger.info("Début import automatique Gmail...");

            GmailImportResultResponse resultat = gmailImportService.importerMaintenant();

            logger.info(
                    "Import Gmail terminé : emailsLus={}, courriersImportes={}, spamsImportes={}, doublonsIgnores={}",
                    resultat.getEmailsLus(),
                    resultat.getCourriersImportes(),
                    resultat.getSpamsImportes(),
                    resultat.getDoublonsIgnores()
            );
        } catch (Exception e) {
            logger.error("Erreur pendant l'import automatique Gmail : {}", e.getMessage(), e);
        }
    }
}