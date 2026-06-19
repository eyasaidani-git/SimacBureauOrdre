package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.CreateReclamationRequest;
import com.simac.simacordre.dto.request.TraiterReclamationRequest;
import com.simac.simacordre.dto.response.ReclamationResponse;
import com.simac.simacordre.enums.CategorieReclamationEnum;
import com.simac.simacordre.enums.StatutReclamationEnum;
import com.simac.simacordre.services.ReclamationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reclamations")
@Tag(name = "Réclamations", description = "API pour la création et le traitement des réclamations")
public class ReclamationController {

    private final ReclamationService reclamationService;

    public ReclamationController(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les réclamations")
    public List<ReclamationResponse> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une réclamation par ID")
    public ReclamationResponse getReclamationById(@PathVariable Long id) {
        return reclamationService.getReclamationById(id);
    }

    @GetMapping("/createur/{createurId}")
    @Operation(summary = "Lister les réclamations créées par un utilisateur")
    public List<ReclamationResponse> getReclamationsByCreateur(@PathVariable Long createurId) {
        return reclamationService.getReclamationsByCreateur(createurId);
    }

    @GetMapping("/categorie/{categorie}")
    @Operation(summary = "Lister les réclamations par catégorie")
    public List<ReclamationResponse> getReclamationsByCategorie(@PathVariable CategorieReclamationEnum categorie) {
        return reclamationService.getReclamationsByCategorie(categorie);
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Lister les réclamations par statut")
    public List<ReclamationResponse> getReclamationsByStatut(@PathVariable StatutReclamationEnum statut) {
        return reclamationService.getReclamationsByStatut(statut);
    }

    @PostMapping
    @Operation(summary = "Créer une réclamation")
    public ReclamationResponse creerReclamation(@RequestBody CreateReclamationRequest request) {
        return reclamationService.creerReclamation(request);
    }

    @PutMapping("/{id}/traiter")
    @Operation(summary = "Traiter une réclamation")
    public ReclamationResponse traiterReclamation(
            @PathVariable Long id,
            @RequestBody TraiterReclamationRequest request
    ) {
        return reclamationService.traiterReclamation(id, request);
    }
}