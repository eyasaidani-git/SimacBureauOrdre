package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.CreateAffectationRequest;
import com.simac.simacordre.dto.response.AffectationResponse;
import com.simac.simacordre.services.AffectationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/api/affectations")
public class AffectationController {

    private final AffectationService affectationService;

    public AffectationController(AffectationService affectationService) {
        this.affectationService = affectationService;
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR')")
    @GetMapping
    public List<AffectationResponse> getAllAffectations() {
        return affectationService.getAllAffectations();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE', 'DIRECTEUR', 'EMPLOYE')")
    @GetMapping("/courrier/{courrierId}")
    public List<AffectationResponse> getAffectationsByCourrier(@PathVariable Long courrierId) {
        return affectationService.getAffectationsByCourrier(courrierId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'RESPONSABLE')")
    @PostMapping
    public AffectationResponse affecterCourrier(@RequestBody CreateAffectationRequest request) {
        return affectationService.affecterCourrier(request);
    }
}