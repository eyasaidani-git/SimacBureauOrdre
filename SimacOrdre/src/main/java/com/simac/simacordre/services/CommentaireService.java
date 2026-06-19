package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.CreateCommentaireRequest;
import com.simac.simacordre.dto.response.CommentaireResponse;
import com.simac.simacordre.entities.Commentaire;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.repositories.CommentaireRepository;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final CourrierRepository courrierRepository;
    private final UtilisateurRepository utilisateurRepository;

    public CommentaireService(
            CommentaireRepository commentaireRepository,
            CourrierRepository courrierRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.commentaireRepository = commentaireRepository;
        this.courrierRepository = courrierRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<CommentaireResponse> getAllCommentaires() {
        return commentaireRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<CommentaireResponse> getCommentairesByCourrier(Long courrierId) {
        return commentaireRepository.findByCourrierIdOrderByCreatedAtDesc(courrierId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CommentaireResponse creerCommentaire(CreateCommentaireRequest request) {
        if (request.getCourrierId() == null) {
            throw new RuntimeException("Le courrier est obligatoire.");
        }

        if (request.getAuteurId() == null) {
            throw new RuntimeException("L'auteur est obligatoire.");
        }

        if (request.getContenu() == null || request.getContenu().isBlank()) {
            throw new RuntimeException("Le contenu est obligatoire.");
        }

        Courrier courrier = courrierRepository.findById(request.getCourrierId())
                .orElseThrow(() -> new RuntimeException("Courrier introuvable."));

        Utilisateur auteur = utilisateurRepository.findById(request.getAuteurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));

        Commentaire commentaire = new Commentaire();
        commentaire.setCourrier(courrier);
        commentaire.setAuteur(auteur);
        commentaire.setContenu(request.getContenu());
        commentaire.setCreatedAt(LocalDateTime.now());

        return toResponse(commentaireRepository.save(commentaire));
    }

    private CommentaireResponse toResponse(Commentaire commentaire) {
        String auteurNomComplet = null;

        if (commentaire.getAuteur() != null) {
            auteurNomComplet = commentaire.getAuteur().getPrenom() + " " + commentaire.getAuteur().getNom();
        }

        return new CommentaireResponse(
                commentaire.getId(),
                commentaire.getCourrier() != null ? commentaire.getCourrier().getId() : null,
                commentaire.getCourrier() != null ? commentaire.getCourrier().getNumeroOrdre() : null,
                commentaire.getAuteur() != null ? commentaire.getAuteur().getId() : null,
                auteurNomComplet,
                commentaire.getContenu(),
                commentaire.getCreatedAt(),
                commentaire.getUpdatedAt()
        );
    }
}