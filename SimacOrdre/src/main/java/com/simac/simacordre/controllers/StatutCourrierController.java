package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.UpdateStatutCourrierRequest;
import com.simac.simacordre.dto.response.UpdateStatutCourrierResponse;
import com.simac.simacordre.services.StatutCourrierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courriers")
@Tag(name = "Statuts des courriers", description = "API pour la mise à jour des statuts des courriers")
public class StatutCourrierController {

    private final StatutCourrierService statutCourrierService;

    public StatutCourrierController(StatutCourrierService statutCourrierService) {
        this.statutCourrierService = statutCourrierService;
    }

    @PutMapping("/{courrierId}/statut")
    @Operation(summary = "Mettre à jour le statut d'un courrier")
    public UpdateStatutCourrierResponse mettreAJourStatut(
            @PathVariable Long courrierId,
            @RequestBody UpdateStatutCourrierRequest request
    ) {
        return statutCourrierService.mettreAJourStatut(courrierId, request);
    }
}