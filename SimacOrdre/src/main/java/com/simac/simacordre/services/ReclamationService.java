package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.CreateReclamationRequest;
import com.simac.simacordre.dto.request.TraiterReclamationRequest;
import com.simac.simacordre.dto.response.ReclamationResponse;
import com.simac.simacordre.entities.Courrier;
import com.simac.simacordre.entities.Reclamation;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.enums.CategorieReclamationEnum;
import com.simac.simacordre.enums.RoleEnum;
import com.simac.simacordre.enums.StatutReclamationEnum;
import com.simac.simacordre.repositories.CourrierRepository;
import com.simac.simacordre.repositories.ReclamationRepository;
import com.simac.simacordre.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReclamationService {

    private final ReclamationRepository reclamationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CourrierRepository courrierRepository;

    public ReclamationService(
            ReclamationRepository reclamationRepository,
            UtilisateurRepository utilisateurRepository,
            CourrierRepository courrierRepository
    ) {
        this.reclamationRepository = reclamationRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.courrierRepository = courrierRepository;
    }

    public List<ReclamationResponse> getAllReclamations() {
        return reclamationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ReclamationResponse getReclamationById(Long id) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation introuvable avec l'id : " + id));

        return toResponse(reclamation);
    }

    public List<ReclamationResponse> getReclamationsByCreateur(Long createurId) {
        return reclamationRepository.findByCreateurId(createurId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ReclamationResponse> getReclamationsByCategorie(CategorieReclamationEnum categorie) {
        return reclamationRepository.findByCategorie(categorie)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ReclamationResponse> getReclamationsByStatut(StatutReclamationEnum statut) {
        return reclamationRepository.findByStatut(statut)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ReclamationResponse creerReclamation(CreateReclamationRequest request) {
        if (request.getCategorie() == null) {
            throw new RuntimeException("La catégorie de réclamation est obligatoire.");
        }

        if (request.getSujet() == null || request.getSujet().isBlank()) {
            throw new RuntimeException("Le sujet de la réclamation est obligatoire.");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new RuntimeException("La description de la réclamation est obligatoire.");
        }

        if (request.getCreateurId() == null) {
            throw new RuntimeException("Le créateur de la réclamation est obligatoire.");
        }

        Utilisateur createur = utilisateurRepository.findById(request.getCreateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur créateur introuvable."));

        verifierDroitCreation(request.getCategorie(), createur);

        Courrier courrier = null;
        if (request.getCourrierId() != null) {
            courrier = courrierRepository.findById(request.getCourrierId())
                    .orElseThrow(() -> new RuntimeException("Courrier introuvable avec l'id : " + request.getCourrierId()));
        }

        Reclamation reclamation = new Reclamation();
        reclamation.setCategorie(request.getCategorie());
        reclamation.setStatut(StatutReclamationEnum.OUVERTE);
        reclamation.setSujet(request.getSujet());
        reclamation.setDescription(request.getDescription());
        reclamation.setCreateur(createur);
        reclamation.setCourrier(courrier);
        reclamation.setCreatedAt(LocalDateTime.now());

        Reclamation saved = reclamationRepository.save(reclamation);

        return toResponse(saved);
    }

    @Transactional
    public ReclamationResponse traiterReclamation(Long reclamationId, TraiterReclamationRequest request) {
        if (request.getTraiteeParId() == null) {
            throw new RuntimeException("L'utilisateur qui traite la réclamation est obligatoire.");
        }

        if (request.getNouveauStatut() == null) {
            throw new RuntimeException("Le nouveau statut est obligatoire.");
        }

        if (
                request.getNouveauStatut() != StatutReclamationEnum.TRAITEE
                        && request.getNouveauStatut() != StatutReclamationEnum.REJETEE
        ) {
            throw new RuntimeException("Le statut final doit être TRAITEE ou REJETEE.");
        }

        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation introuvable avec l'id : " + reclamationId));

        Utilisateur traiteePar = utilisateurRepository.findById(request.getTraiteeParId())
                .orElseThrow(() -> new RuntimeException("Utilisateur traitant introuvable."));

        verifierDroitTraitement(reclamation.getCategorie(), traiteePar);

        reclamation.setStatut(request.getNouveauStatut());
        reclamation.setTraiteePar(traiteePar);
        reclamation.setReponse(request.getReponse());
        reclamation.setUpdatedAt(LocalDateTime.now());

        Reclamation saved = reclamationRepository.save(reclamation);

        return toResponse(saved);
    }

    private void verifierDroitCreation(CategorieReclamationEnum categorie, Utilisateur createur) {
        RoleEnum role = createur.getRole();

        if (categorie == CategorieReclamationEnum.SYSTEME_APPLICATION) {
            boolean autorise =
                    role == RoleEnum.AGENT
                            || role == RoleEnum.EMPLOYE
                            || role == RoleEnum.RESPONSABLE
                            || role == RoleEnum.DIRECTEUR;

            if (!autorise) {
                throw new RuntimeException("Seuls l'agent, l'employé, le responsable et le directeur peuvent créer une réclamation système/application.");
            }
        }

        if (categorie == CategorieReclamationEnum.SPAM) {
            if (role != RoleEnum.AGENT) {
                throw new RuntimeException("Seul l'agent peut créer une réclamation spam.");
            }
        }
    }

    private void verifierDroitTraitement(CategorieReclamationEnum categorie, Utilisateur traiteePar) {
        RoleEnum role = traiteePar.getRole();

        if (categorie == CategorieReclamationEnum.SYSTEME_APPLICATION) {
            if (role != RoleEnum.ADMIN) {
                throw new RuntimeException("Seul l'administrateur peut traiter une réclamation système/application.");
            }
        }

        if (categorie == CategorieReclamationEnum.SPAM) {
            boolean autorise = role == RoleEnum.ADMIN || role == RoleEnum.RESPONSABLE;

            if (!autorise) {
                throw new RuntimeException("Une réclamation spam peut être traitée seulement par l'administrateur ou le responsable.");
            }
        }
    }

    private ReclamationResponse toResponse(Reclamation reclamation) {
        String createurNomComplet = null;
        String createurRole = null;

        if (reclamation.getCreateur() != null) {
            createurNomComplet = reclamation.getCreateur().getPrenom() + " " + reclamation.getCreateur().getNom();
            createurRole = reclamation.getCreateur().getRole().name();
        }

        String traiteeParNomComplet = null;
        String traiteeParRole = null;

        if (reclamation.getTraiteePar() != null) {
            traiteeParNomComplet = reclamation.getTraiteePar().getPrenom() + " " + reclamation.getTraiteePar().getNom();
            traiteeParRole = reclamation.getTraiteePar().getRole().name();
        }

        Long courrierId = null;
        String numeroOrdre = null;

        if (reclamation.getCourrier() != null) {
            courrierId = reclamation.getCourrier().getId();
            numeroOrdre = reclamation.getCourrier().getNumeroOrdre();
        }

        return new ReclamationResponse(
                reclamation.getId(),
                reclamation.getCategorie(),
                reclamation.getStatut(),
                reclamation.getSujet(),
                reclamation.getDescription(),
                courrierId,
                numeroOrdre,
                reclamation.getCreateur() != null ? reclamation.getCreateur().getId() : null,
                createurNomComplet,
                createurRole,
                reclamation.getTraiteePar() != null ? reclamation.getTraiteePar().getId() : null,
                traiteeParNomComplet,
                traiteeParRole,
                reclamation.getReponse(),
                reclamation.getCreatedAt(),
                reclamation.getUpdatedAt()
        );
    }
}