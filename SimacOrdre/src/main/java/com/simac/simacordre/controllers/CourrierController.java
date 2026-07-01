package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.UpdateCourrierDescriptionRequest;
import com.simac.simacordre.dto.request.CreateCourrierRequest;
import com.simac.simacordre.dto.response.CourrierResponse;
import com.simac.simacordre.services.CourrierService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courriers")
public class CourrierController {

    private final CourrierService courrierService;

    public CourrierController(CourrierService courrierService) {
        this.courrierService = courrierService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR', 'EMPLOYE')")
    @GetMapping
    public List<CourrierResponse> getAllCourriers() {
        return courrierService.getAllCourriers();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR', 'EMPLOYE')")
    @GetMapping("/{id}")
    public CourrierResponse getCourrierById(@PathVariable Long id) {
        return courrierService.getCourrierById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @PostMapping
    public CourrierResponse createCourrier(@RequestBody CreateCourrierRequest request) {
        return courrierService.createCourrier(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @PatchMapping("/{id}/description")
    public Map<String, String> modifierDescriptionCourrier(
            @PathVariable Long id,
            @RequestBody UpdateCourrierDescriptionRequest request
    ) {
        courrierService.modifierDescriptionCourrier(id, request.getDescription());

        return Map.of("message", "Description du courrier modifiée avec succès.");
    }
}