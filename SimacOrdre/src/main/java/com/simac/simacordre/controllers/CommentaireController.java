package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.CreateCommentaireRequest;
import com.simac.simacordre.dto.response.CommentaireResponse;
import com.simac.simacordre.services.CommentaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
@Tag(name = "Commentaires", description = "API pour la gestion des commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les commentaires")
    public List<CommentaireResponse> getAllCommentaires() {
        return commentaireService.getAllCommentaires();
    }

    @GetMapping("/courrier/{courrierId}")
    @Operation(summary = "Lister les commentaires d'un courrier")
    public List<CommentaireResponse> getCommentairesByCourrier(@PathVariable Long courrierId) {
        return commentaireService.getCommentairesByCourrier(courrierId);
    }

    @PostMapping
    @Operation(summary = "Ajouter un commentaire")
    public CommentaireResponse creerCommentaire(@RequestBody CreateCommentaireRequest request) {
        return commentaireService.creerCommentaire(request);
    }
}