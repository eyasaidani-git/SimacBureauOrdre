package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.UpdateStatutCourrierRequest;
import com.simac.simacordre.dto.response.UpdateStatutCourrierResponse;
import com.simac.simacordre.services.StatutCourrierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@RequestMapping("/api/courriers")
@Tag(name = "Statuts des courriers", description = "API pour la mise à jour des statuts des courriers")
public class StatutCourrierController {

    private final StatutCourrierService statutCourrierService;

    public StatutCourrierController(StatutCourrierService statutCourrierService) {
        this.statutCourrierService = statutCourrierService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR')")
    @PutMapping("/{courrierId}/statut")
    public UpdateStatutCourrierResponse updateStatutCourrier(
            @PathVariable Long courrierId,
            @RequestBody UpdateStatutCourrierRequest request
    ) {
        return statutCourrierService.mettreAJourStatut(courrierId, request);
    }
}