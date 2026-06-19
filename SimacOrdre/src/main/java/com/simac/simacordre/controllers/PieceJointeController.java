package com.simac.simacordre.controllers;

import com.simac.simacordre.dto.request.CreatePieceJointeRequest;
import com.simac.simacordre.dto.response.PieceJointeResponse;
import com.simac.simacordre.services.PieceJointeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pieces-jointes")
@Tag(name = "Pièces jointes", description = "API pour la gestion des pièces jointes")
public class PieceJointeController {

    private final PieceJointeService pieceJointeService;

    public PieceJointeController(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les pièces jointes")
    public List<PieceJointeResponse> getAllPiecesJointes() {
        return pieceJointeService.getAllPiecesJointes();
    }

    @GetMapping("/courrier/{courrierId}")
    @Operation(summary = "Lister les pièces jointes d'un courrier")
    public List<PieceJointeResponse> getPiecesJointesByCourrier(@PathVariable Long courrierId) {
        return pieceJointeService.getPiecesJointesByCourrier(courrierId);
    }

    @PostMapping
    @Operation(summary = "Ajouter une pièce jointe")
    public PieceJointeResponse ajouterPieceJointe(@RequestBody CreatePieceJointeRequest request) {
        return pieceJointeService.ajouterPieceJointe(request);
    }
}