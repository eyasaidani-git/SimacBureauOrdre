package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.CreateAffectationRequest;
import com.simac.simacordre.dto.response.AffectationResponse;
import com.simac.simacordre.entities.Affectation;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.Departement;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.enums.StatutCourrierEnum;
import com.simac.simacordre.repositories.AffectationRepository;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.DepartementRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AffectationService {

    private final AffectationRepository affectationRepository;
    private final CourrierRepository courrierRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final DepartementRepository departementRepository;

    public AffectationService(
            AffectationRepository affectationRepository,
            CourrierRepository courrierRepository,
            UtilisateurRepository utilisateurRepository,
            DepartementRepository departementRepository
    ) {
        this.affectationRepository = affectationRepository;
        this.courrierRepository = courrierRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.departementRepository = departementRepository;
    }

    public List<AffectationResponse> getAllAffectations() {
        return affectationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AffectationResponse> getAffectationsByCourrier(Long courrierId) {
        return affectationRepository.findByCourrierId(courrierId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AffectationResponse affecterCourrier(CreateAffectationRequest request) {
        Courrier courrier = courrierRepository.findById(request.getCourrierId())
                .orElseThrow(() -> new RuntimeException("Courrier introuvable"));

        Utilisateur affectePar = utilisateurRepository.findById(request.getAffecteParId())
                .orElseThrow(() -> new RuntimeException("Utilisateur affectant introuvable"));

        Utilisateur affecteAUtilisateur = null;
        if (request.getAffecteAUtilisateurId() != null) {
            affecteAUtilisateur = utilisateurRepository.findById(request.getAffecteAUtilisateurId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur destinataire introuvable"));
        }

        Departement affecteADepartement = null;
        if (request.getAffecteADepartementId() != null) {
            affecteADepartement = departementRepository.findById(request.getAffecteADepartementId())
                    .orElseThrow(() -> new RuntimeException("Département destinataire introuvable"));
        }

        affectationRepository.findByCourrierIdAndActiveTrue(courrier.getId())
                .forEach(ancienneAffectation -> {
                    ancienneAffectation.setActive(false);
                    affectationRepository.save(ancienneAffectation);
                });

        Affectation affectation = new Affectation();
        affectation.setCourrier(courrier);
        affectation.setAffectePar(affectePar);
        affectation.setAffecteAUtilisateur(affecteAUtilisateur);
        affectation.setAffecteADepartement(affecteADepartement);
        affectation.setCommentaire(request.getCommentaire());
        affectation.setActive(true);
        affectation.setCreatedAt(LocalDateTime.now());

        courrier.setStatut(StatutCourrierEnum.EN_COURS);
        courrierRepository.save(courrier);

        Affectation saved = affectationRepository.save(affectation);

        return toResponse(saved);
    }

    private AffectationResponse toResponse(Affectation affectation) {
        String affecteParNom = affectation.getAffectePar() != null
                ? affectation.getAffectePar().getPrenom() + " " + affectation.getAffectePar().getNom()
                : null;

        String affecteAUtilisateurNom = affectation.getAffecteAUtilisateur() != null
                ? affectation.getAffecteAUtilisateur().getPrenom() + " " + affectation.getAffecteAUtilisateur().getNom()
                : null;

        String affecteADepartementNom = affectation.getAffecteADepartement() != null
                ? affectation.getAffecteADepartement().getNom()
                : null;

        return new AffectationResponse(
                affectation.getId(),
                affectation.getCourrier() != null ? affectation.getCourrier().getId() : null,
                affectation.getCourrier() != null ? affectation.getCourrier().getNumeroOrdre() : null,
                affecteParNom,
                affecteAUtilisateurNom,
                affecteADepartementNom,
                affectation.getCommentaire(),
                affectation.getActive(),
                affectation.getCreatedAt()
        );
    }
}