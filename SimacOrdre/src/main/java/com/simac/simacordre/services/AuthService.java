package com.simac.simacordre.services;

import com.simac.simacordre.dto.request.ChangePasswordFirstLoginRequest;
import com.simac.simacordre.dto.request.LoginRequest;
import com.simac.simacordre.dto.request.ResendEmailVerificationCodeRequest;
import com.simac.simacordre.dto.request.VerifyEmailRequest;
import com.simac.simacordre.dto.response.LoginResponse;
import com.simac.simacordre.dto.response.UtilisateurResponse;
import com.simac.simacordre.entities.Utilisateur;
import com.simac.simacordre.enums.StatutUtilisateurEnum;
import com.simac.simacordre.repositories.UtilisateurRepository;
import com.simac.simacordre.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    public AuthService(
            UtilisateurRepository utilisateurRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

        boolean motDePasseValide = verifierMotDePasse(
                request.getMotDePasse(),
                utilisateur.getMotDePasse()
        );

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

    @Transactional
    public String envoyerCodeVerificationEmail(ResendEmailVerificationCodeRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("L'email est obligatoire.");
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec cet email."));

        if (Boolean.TRUE.equals(utilisateur.getEmailVerifie())) {
            return "L'adresse email est déjà vérifiée.";
        }

        String code = genererCodeSixChiffres();

        utilisateur.setCodeVerificationEmail(code);
        utilisateur.setCodeVerificationExpireAt(LocalDateTime.now().plusMinutes(10));

        utilisateurRepository.save(utilisateur);

        String nomComplet = utilisateur.getPrenom() + " " + utilisateur.getNom();

        emailService.envoyerCodeVerificationEmail(
                utilisateur.getEmail(),
                nomComplet,
                code
        );

        return "Code de vérification envoyé par email.";
    }

    @Transactional
    public String verifierEmail(VerifyEmailRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("L'email est obligatoire.");
        }

        if (request.getCode() == null || request.getCode().isBlank()) {
            throw new RuntimeException("Le code de vérification est obligatoire.");
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec cet email."));

        if (Boolean.TRUE.equals(utilisateur.getEmailVerifie())) {
            return "L'adresse email est déjà vérifiée.";
        }

        if (utilisateur.getCodeVerificationEmail() == null ||
                utilisateur.getCodeVerificationExpireAt() == null) {
            throw new RuntimeException("Aucun code de vérification n'a été généré.");
        }

        if (utilisateur.getCodeVerificationExpireAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Le code de vérification a expiré.");
        }

        if (!utilisateur.getCodeVerificationEmail().equals(request.getCode())) {
            throw new RuntimeException("Code de vérification incorrect.");
        }

        utilisateur.setEmailVerifie(true);
        utilisateur.setCodeVerificationEmail(null);
        utilisateur.setCodeVerificationExpireAt(null);

        utilisateurRepository.save(utilisateur);

        return "Adresse email vérifiée avec succès.";
    }

    @Transactional
    public String changerMotDePassePremiereConnexion(ChangePasswordFirstLoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("L'email est obligatoire.");
        }

        if (request.getAncienMotDePasse() == null || request.getAncienMotDePasse().isBlank()) {
            throw new RuntimeException("L'ancien mot de passe est obligatoire.");
        }

        if (request.getNouveauMotDePasse() == null || request.getNouveauMotDePasse().isBlank()) {
            throw new RuntimeException("Le nouveau mot de passe est obligatoire.");
        }

        if (request.getConfirmationMotDePasse() == null || request.getConfirmationMotDePasse().isBlank()) {
            throw new RuntimeException("La confirmation du mot de passe est obligatoire.");
        }

        if (!request.getNouveauMotDePasse().equals(request.getConfirmationMotDePasse())) {
            throw new RuntimeException("La confirmation du mot de passe ne correspond pas.");
        }

        if (request.getNouveauMotDePasse().length() < 8) {
            throw new RuntimeException("Le nouveau mot de passe doit contenir au moins 8 caractères.");
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec cet email."));

        if (!Boolean.TRUE.equals(utilisateur.getEmailVerifie())) {
            throw new RuntimeException("Veuillez vérifier votre adresse email avant de changer le mot de passe.");
        }

        if (!Boolean.TRUE.equals(utilisateur.getPremiereConnexion())) {
            throw new RuntimeException("Ce compte n'est pas en première connexion.");
        }

        boolean ancienMotDePasseValide = verifierMotDePasse(
                request.getAncienMotDePasse(),
                utilisateur.getMotDePasse()
        );

        if (!ancienMotDePasseValide) {
            throw new RuntimeException("Ancien mot de passe incorrect.");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(request.getNouveauMotDePasse()));
        utilisateur.setPremiereConnexion(false);
        utilisateur.setCodeVerificationEmail(null);
        utilisateur.setCodeVerificationExpireAt(null);

        utilisateurRepository.save(utilisateur);

        return "Mot de passe changé avec succès. Vous pouvez maintenant vous connecter normalement.";
    }

    private String genererCodeSixChiffres() {
        int code = secureRandom.nextInt(1_000_000);
        return String.format("%06d", code);
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