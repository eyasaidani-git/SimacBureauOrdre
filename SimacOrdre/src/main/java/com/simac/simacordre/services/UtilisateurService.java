package com.simac.simacordre.services;

import com.simac.simacordre.dto.response.UtilisateurResponse;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<UtilisateurResponse> getAllUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UtilisateurResponse getUtilisateurById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec id : " + id));

        return toResponse(utilisateur);
    }

    private UtilisateurResponse toResponse(Utilisateur utilisateur) {
        Long departementId = null;
        String departementNom = null;

        if (utilisateur.getDepartement() != null) {
            departementId = utilisateur.getDepartement().getId();
            departementNom = utilisateur.getDepartement().getNom();
        }

        Long responsableId = null;
        String responsableNomComplet = null;

        if (utilisateur.getResponsable() != null) {
            responsableId = utilisateur.getResponsable().getId();
            responsableNomComplet = utilisateur.getResponsable().getPrenom()
                    + " "
                    + utilisateur.getResponsable().getNom();
        }

        return new UtilisateurResponse(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getTelephone(),
                utilisateur.getRole(),
                utilisateur.getStatut(),
                utilisateur.getPremiereConnexion(),
                utilisateur.getEmailVerifie(),
                departementId,
                departementNom,
                responsableId,
                responsableNomComplet,
                utilisateur.getPhotoProfil(),
                utilisateur.getCreatedAt()
        );
    }
}