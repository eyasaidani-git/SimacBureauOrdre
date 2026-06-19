package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.UpdateStatutCourrierRequest;
import com.simac.simacordre.dto.response.UpdateStatutCourrierResponse;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.HistoriqueStatut;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.enums.StatutCourrierEnum;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.HistoriqueStatutRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StatutCourrierService {

    private final CourrierRepository courrierRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final HistoriqueStatutRepository historiqueStatutRepository;

    public StatutCourrierService(
            CourrierRepository courrierRepository,
            UtilisateurRepository utilisateurRepository,
            HistoriqueStatutRepository historiqueStatutRepository
    ) {
        this.courrierRepository = courrierRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.historiqueStatutRepository = historiqueStatutRepository;
    }

    @Transactional
    public UpdateStatutCourrierResponse mettreAJourStatut(
            Long courrierId,
            UpdateStatutCourrierRequest request
    ) {
        if (request.getNouveauStatut() == null) {
            throw new RuntimeException("Le nouveau statut est obligatoire.");
        }

        if (request.getModifieParId() == null) {
            throw new RuntimeException("L'utilisateur qui modifie le statut est obligatoire.");
        }

        Courrier courrier = courrierRepository.findById(courrierId)
                .orElseThrow(() -> new RuntimeException("Courrier introuvable avec l'id : " + courrierId));

        Utilisateur modifiePar = utilisateurRepository.findById(request.getModifieParId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + request.getModifieParId()));

        StatutCourrierEnum ancienStatut = courrier.getStatut();
        StatutCourrierEnum nouveauStatut = request.getNouveauStatut();

        if (ancienStatut == nouveauStatut) {
            throw new RuntimeException("Le courrier possède déjà ce statut.");
        }

        courrier.setStatut(nouveauStatut);
        Courrier courrierSauvegarde = courrierRepository.save(courrier);

        HistoriqueStatut historiqueStatut = new HistoriqueStatut();
        historiqueStatut.setCourrier(courrierSauvegarde);
        historiqueStatut.setAncienStatut(ancienStatut);
        historiqueStatut.setNouveauStatut(nouveauStatut);
        historiqueStatut.setModifiePar(modifiePar);
        historiqueStatut.setCommentaire(request.getCommentaire());
        historiqueStatut.setCreatedAt(LocalDateTime.now());

        HistoriqueStatut historiqueSauvegarde = historiqueStatutRepository.save(historiqueStatut);

        String nomComplet = modifiePar.getPrenom() + " " + modifiePar.getNom();

        return new UpdateStatutCourrierResponse(
                courrierSauvegarde.getId(),
                courrierSauvegarde.getNumeroOrdre(),
                ancienStatut,
                nouveauStatut,
                modifiePar.getId(),
                nomComplet,
                historiqueSauvegarde.getCommentaire(),
                historiqueSauvegarde.getCreatedAt(),
                "Statut du courrier mis à jour avec succès."
        );
    }
}