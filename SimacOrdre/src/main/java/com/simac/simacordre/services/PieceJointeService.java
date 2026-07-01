package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.CreatePieceJointeRequest;
import com.simac.simacordre.dto.response.PieceJointeResponse;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.PieceJointe;
import com.simac.simacordre.enums.TypeDocumentPieceJointeEnum;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.PieceJointeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PieceJointeService {

    private final PieceJointeRepository pieceJointeRepository;
    private final CourrierRepository courrierRepository;

    public PieceJointeService(
            PieceJointeRepository pieceJointeRepository,
            CourrierRepository courrierRepository
    ) {
        this.pieceJointeRepository = pieceJointeRepository;
        this.courrierRepository = courrierRepository;
    }

    public List<PieceJointeResponse> getAllPiecesJointes() {
        return pieceJointeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PieceJointeResponse> getPiecesJointesByCourrier(Long courrierId) {
        return pieceJointeRepository.findByCourrierIdOrderByUploadedAtDesc(courrierId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PieceJointeResponse ajouterPieceJointe(CreatePieceJointeRequest request) {
        if (request.getCourrierId() == null) {
            throw new RuntimeException("Le courrier est obligatoire.");
        }

        if (request.getNomFichier() == null || request.getNomFichier().isBlank()) {
            throw new RuntimeException("Le nom du fichier est obligatoire.");
        }

        if (request.getTypeFichier() == null) {
            throw new RuntimeException("Le type du fichier est obligatoire.");
        }

        if (request.getCheminFichier() == null || request.getCheminFichier().isBlank()) {
            throw new RuntimeException("Le chemin du fichier est obligatoire.");
        }

        if (request.getTaille() == null) {
            throw new RuntimeException("La taille du fichier est obligatoire.");
        }

        Courrier courrier = courrierRepository.findById(request.getCourrierId())
                .orElseThrow(() -> new RuntimeException("Courrier introuvable."));

        PieceJointe pieceJointe = new PieceJointe();
        pieceJointe.setCourrier(courrier);
        pieceJointe.setNomFichier(request.getNomFichier());
        pieceJointe.setTypeFichier(request.getTypeFichier());
        pieceJointe.setCheminFichier(request.getCheminFichier());
        pieceJointe.setTaille(request.getTaille());
        pieceJointe.setTypeDocument(
                request.getTypeDocument() != null ? request.getTypeDocument() : TypeDocumentPieceJointeEnum.AUTRE
        );
        pieceJointe.setReferenceDocument(request.getReferenceDocument());
        pieceJointe.setObservation(request.getObservation());
        pieceJointe.setUploadedAt(LocalDateTime.now());

        return toResponse(pieceJointeRepository.save(pieceJointe));
    }

    private PieceJointeResponse toResponse(PieceJointe pieceJointe) {
        return new PieceJointeResponse(
                pieceJointe.getId(),
                pieceJointe.getCourrier() != null ? pieceJointe.getCourrier().getId() : null,
                pieceJointe.getCourrier() != null ? pieceJointe.getCourrier().getNumeroOrdre() : null,
                pieceJointe.getNomFichier(),
                pieceJointe.getTypeFichier(),
                pieceJointe.getCheminFichier(),
                pieceJointe.getTaille(),
                pieceJointe.getTypeDocument(),
                pieceJointe.getUploadedAt(),
                pieceJointe.getReferenceDocument(),
                pieceJointe.getObservation()
        );
    }
}