package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.response.UtilisateurResponse;
import com.simac.simacordre.services.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@Tag(name = "Utilisateurs", description = "API pour la gestion des utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs sans afficher les mots de passe")
    public List<UtilisateurResponse> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un utilisateur par ID sans afficher le mot de passe")
    public UtilisateurResponse getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id);
    }
}