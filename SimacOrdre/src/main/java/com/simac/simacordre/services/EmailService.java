package com.simac.simacordre.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    public void envoyerEmailSimple(String to, String subject, String message) {
        if (to == null || to.isBlank()) {
            throw new RuntimeException("L'adresse email du destinataire est obligatoire.");
        }

        if (subject == null || subject.isBlank()) {
            throw new RuntimeException("Le sujet de l'email est obligatoire.");
        }

        if (message == null || message.isBlank()) {
            throw new RuntimeException("Le message de l'email est obligatoire.");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }

    public void envoyerCodeVerificationEmail(String to, String nomComplet, String code) {
        String subject = "Vérification de votre adresse email";

        String message = """
                Bonjour %s,

                Votre code de vérification est : %s

                Ce code est valable pendant quelques minutes.

                Cordialement,
                Bureau d'Ordre Digital
                """.formatted(nomComplet, code);

        envoyerEmailSimple(to, subject, message);
    }

    public void envoyerCodeResetPassword(String to, String nomComplet, String code) {
        String subject = "Réinitialisation de votre mot de passe";

        String message = """
                Bonjour %s,

                Vous avez demandé la réinitialisation de votre mot de passe.

                Votre code est : %s

                Si vous n'êtes pas à l'origine de cette demande, ignorez cet email.

                Cordialement,
                Bureau d'Ordre Digital
                """.formatted(nomComplet, code);

        envoyerEmailSimple(to, subject, message);
    }
}