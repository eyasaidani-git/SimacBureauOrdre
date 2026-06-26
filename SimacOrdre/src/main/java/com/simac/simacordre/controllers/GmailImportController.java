package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.response.GmailImportResultResponse;
import com.simac.simacordre.dto.response.GmailMessagePreviewResponse;
import com.simac.simacordre.services.GmailImportService;
import com.simac.simacordre.services.GmailReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gmail-import")
@Tag(name = "Import Gmail", description = "API pour tester et lancer l'import automatique des emails Gmail")
public class GmailImportController {

    private final GmailReaderService gmailReaderService;
    private final GmailImportService gmailImportService;

    public GmailImportController(
            GmailReaderService gmailReaderService,
            GmailImportService gmailImportService
    ) {
        this.gmailReaderService = gmailReaderService;
        this.gmailImportService = gmailImportService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/preview")
    @Operation(summary = "Prévisualiser les derniers emails Gmail reçus")
    public List<GmailMessagePreviewResponse> previewDerniersEmails() {
        return gmailReaderService.lireDerniersEmailsPourPreview();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/import-now")
    @Operation(summary = "Importer maintenant les emails Gmail dans la base")
    public GmailImportResultResponse importerMaintenant() {
        return gmailImportService.importerMaintenant();
    }
}