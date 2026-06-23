package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.LoginRequest;
import com.simac.simacordre.dto.response.LoginResponse;
import com.simac.simacordre.dto.response.UtilisateurResponse;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.enums.StatutUtilisateurEnum;
import com.simac.simacordre.repositories.UtilisateurRepository;
import com.simac.simacordre.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UtilisateurRepository utilisateurRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("L'email est obligatoire.");
        }

        if (request.getMotDePasse() == null || request.getMotDePasse().isBlank()) {
            throw new RuntimeException("Le mot de passe est obligatoire.");
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect."));

        if (utilisateur.getStatut() != StatutUtilisateurEnum.ACTIF) {
            throw new RuntimeException("Votre compte n'est pas actif.");
        }

        boolean motDePasseValide = verifierMotDePasse(request.getMotDePasse(), utilisateur.getMotDePasse());

        if (!motDePasseValide) {
            throw new RuntimeException("Email ou mot de passe incorrect.");
        }

        if (!isBCryptPassword(utilisateur.getMotDePasse())) {
            utilisateur.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
            utilisateurRepository.save(utilisateur);
        }

        String token = jwtService.generateToken(utilisateur);

        return new LoginResponse(token, toUtilisateurResponse(utilisateur));
    }

    private boolean verifierMotDePasse(String motDePasseSaisi, String motDePasseStocke) {
        if (motDePasseStocke == null) {
            return false;
        }

        if (isBCryptPassword(motDePasseStocke)) {
            return passwordEncoder.matches(motDePasseSaisi, motDePasseStocke);
        }

        return motDePasseSaisi.equals(motDePasseStocke);
    }

    private boolean isBCryptPassword(String motDePasse) {
        return motDePasse != null
                && (motDePasse.startsWith("$2a$")
                || motDePasse.startsWith("$2b$")
                || motDePasse.startsWith("$2y$"));
    }

    private UtilisateurResponse toUtilisateurResponse(Utilisateur utilisateur) {
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
                departementId,
                departementNom,
                responsableId,
                responsableNomComplet,
                utilisateur.getPhotoProfil(),
                utilisateur.getCreatedAt()
        );
    }
}