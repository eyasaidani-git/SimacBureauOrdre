package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.CreateCourrierRequest;
import com.simac.simacordre.dto.response.CourrierResponse;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.Departement;
import com.simac.simacordre.entities.NumeroOrdreSeq;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.enums.PrioriteEnum;
import com.simac.simacordre.enums.StatutCourrierEnum;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.DepartementRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourrierService {

    private final CourrierRepository courrierRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final DepartementRepository departementRepository;
    private final NumeroOrdreService numeroOrdreService;

    public CourrierService(
            CourrierRepository courrierRepository,
            UtilisateurRepository utilisateurRepository,
            DepartementRepository departementRepository,
            NumeroOrdreService numeroOrdreService
    ) {
        this.courrierRepository = courrierRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.departementRepository = departementRepository;
        this.numeroOrdreService = numeroOrdreService;
    }

    public List<CourrierResponse> getAllCourriers() {
        return courrierRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CourrierResponse getCourrierById(Long id) {
        Courrier courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courrier introuvable avec id : " + id));

        return toResponse(courrier);
    }

    @Transactional
    public CourrierResponse createCourrier(CreateCourrierRequest request) {
        Utilisateur createur = utilisateurRepository.findById(request.getCreateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur créateur introuvable"));

        Departement departement = departementRepository.findById(request.getDepartementId())
                .orElseThrow(() -> new RuntimeException("Département introuvable"));

        LocalDate dateReception = request.getDateReception() != null
                ? request.getDateReception()
                : LocalDate.now();

        NumeroOrdreService.ResultatNumeroOrdre resultatNumeroOrdre =
                numeroOrdreService.genererNumeroOrdre(dateReception);

        String numeroOrdre = resultatNumeroOrdre.getNumeroOrdre();
        NumeroOrdreSeq sequence = resultatNumeroOrdre.getNumeroOrdreSeq();

        Courrier courrier = new Courrier();
        courrier.setNumeroOrdre(numeroOrdre);
        courrier.setNumeroOrdreSeq(sequence);
        courrier.setObjet(request.getObjet());
        courrier.setExpediteur(request.getExpediteur());
        courrier.setDestination(request.getDestination());
        courrier.setType(request.getType());
        courrier.setStatut(StatutCourrierEnum.RECU);
        courrier.setPriorite(request.getPriorite() != null ? request.getPriorite() : PrioriteEnum.NORMALE);
        courrier.setDateReception(dateReception);
        courrier.setDateLimite(request.getDateLimite());
        courrier.setDescription(request.getDescription());
        courrier.setArchive(false);
        courrier.setCreateur(createur);
        courrier.setDepartement(departement);
        courrier.setCreatedAt(LocalDateTime.now());

        Courrier saved = courrierRepository.save(courrier);

        return toResponse(saved);
    }

    private CourrierResponse toResponse(Courrier courrier) {
        String createurNomComplet = courrier.getCreateur() != null
                ? courrier.getCreateur().getPrenom() + " " + courrier.getCreateur().getNom()
                : null;

        String departementNom = courrier.getDepartement() != null
                ? courrier.getDepartement().getNom()
                : null;

        return new CourrierResponse(
                courrier.getId(),
                courrier.getNumeroOrdre(),
                courrier.getObjet(),
                courrier.getExpediteur(),
                courrier.getDestination(),
                courrier.getType(),
                courrier.getStatut(),
                courrier.getPriorite(),
                courrier.getDateReception(),
                courrier.getDateLimite(),
                courrier.getDescription(),
                courrier.getArchive(),
                createurNomComplet,
                departementNom,
                courrier.getCreatedAt()
        );
    }
}