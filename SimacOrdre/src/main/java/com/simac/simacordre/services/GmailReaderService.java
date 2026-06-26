package com.simac.simacordre.services;

import com.simac.simacordre.dto.response.GmailMessagePreviewResponse;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class GmailReaderService {

    @Value("${app.gmail.imap.host}")
    private String imapHost;

    @Value("${app.gmail.imap.port}")
    private String imapPort;

    @Value("${app.gmail.imap.username}")
    private String imapUsername;

    @Value("${app.gmail.imap.password}")
    private String imapPassword;

    @Value("${app.gmail.import.preview-limit:10}")
    private int previewLimit;

    public List<GmailMessagePreviewResponse> lireDerniersEmailsPourPreview() {
        List<GmailMessagePreviewResponse> resultats = new ArrayList<>();

        Store store = null;

        try {
            store = ouvrirConnexionGmail();

            resultats.addAll(lireDossier(store, "INBOX", false));

            Folder dossierSpam = trouverDossierSpam(store);
            if (dossierSpam != null && dossierSpam.exists()) {
                resultats.addAll(lireDossier(dossierSpam, true));
            }

            return resultats;
        } catch (Exception e) {
            throw new RuntimeException("Erreur pendant la lecture Gmail IMAP : " + e.getMessage(), e);
        } finally {
            fermerStore(store);
        }
    }

    private Store ouvrirConnexionGmail() throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", imapHost);
        properties.put("mail.imaps.port", imapPort);
        properties.put("mail.imaps.ssl.enable", "true");
        properties.put("mail.imaps.auth", "true");

        Session session = Session.getInstance(properties);
        Store store = session.getStore("imaps");

        store.connect(imapHost, imapUsername, imapPassword);

        return store;
    }

    private List<GmailMessagePreviewResponse> lireDossier(
            Store store,
            String nomDossier,
            boolean spamDossier
    ) throws MessagingException, IOException {
        Folder folder = store.getFolder(nomDossier);

        if (folder == null || !folder.exists()) {
            return List.of();
        }

        return lireDossier(folder, spamDossier);
    }

    private List<GmailMessagePreviewResponse> lireDossier(
            Folder folder,
            boolean spamDossier
    ) throws MessagingException, IOException {
        List<GmailMessagePreviewResponse> resultats = new ArrayList<>();

        folder.open(Folder.READ_ONLY);

        try {
            int totalMessages = folder.getMessageCount();

            if (totalMessages == 0) {
                return resultats;
            }

            int start = Math.max(1, totalMessages - previewLimit + 1);
            Message[] messages = folder.getMessages(start, totalMessages);

            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];

                String messageId = extraireMessageId(message, folder.getFullName());
                String sujet = message.getSubject();
                String expediteur = extraireExpediteur(message);
                LocalDateTime dateMessage = extraireDateMessage(message);

                String contenu = extraireTexte(message);
                String apercu = creerApercu(contenu);

                GmailMessagePreviewResponse response = new GmailMessagePreviewResponse(
                        messageId,
                        folder.getFullName(),
                        sujet,
                        expediteur,
                        dateMessage,
                        apercu,
                        spamDossier
                );

                resultats.add(response);
            }

            return resultats;
        } finally {
            folder.close(false);
        }
    }

    private Folder trouverDossierSpam(Store store) throws MessagingException {
        String[] nomsPossibles = {
                "[Gmail]/Spam",
                "[Google Mail]/Spam",
                "Spam",
                "Junk",
                "Pourriels",
                "Courrier indésirable"
        };

        for (String nom : nomsPossibles) {
            Folder folder = store.getFolder(nom);

            if (folder != null && folder.exists()) {
                return folder;
            }
        }

        Folder[] folders = store.getDefaultFolder().list("*");

        for (Folder folder : folders) {
            String nom = folder.getFullName().toLowerCase();

            if (nom.contains("spam")
                    || nom.contains("junk")
                    || nom.contains("pourriel")
                    || nom.contains("indésirable")
                    || nom.contains("indesirable")) {
                return folder;
            }
        }

        return null;
    }

    private String extraireMessageId(Message message, String dossierGmail) throws MessagingException {
        String[] headers = message.getHeader("Message-ID");

        if (headers != null && headers.length > 0 && headers[0] != null && !headers[0].isBlank()) {
            return headers[0];
        }

        String sujet = message.getSubject() != null ? message.getSubject() : "sans-sujet";
        Date date = message.getReceivedDate() != null ? message.getReceivedDate() : message.getSentDate();

        return dossierGmail + "-" + message.getMessageNumber() + "-" + sujet + "-" + date;
    }

    private String extraireExpediteur(Message message) throws MessagingException {
        Address[] adresses = message.getFrom();

        if (adresses == null || adresses.length == 0) {
            return "Expéditeur inconnu";
        }

        Address adresse = adresses[0];

        if (adresse instanceof InternetAddress internetAddress) {
            String personal = internetAddress.getPersonal();
            String email = internetAddress.getAddress();

            if (personal != null && !personal.isBlank()) {
                return personal + " <" + email + ">";
            }

            return email;
        }

        return adresse.toString();
    }

    private LocalDateTime extraireDateMessage(Message message) throws MessagingException {
        Date date = message.getReceivedDate();

        if (date == null) {
            date = message.getSentDate();
        }

        if (date == null) {
            return LocalDateTime.now();
        }

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private String extraireTexte(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/plain")) {
            Object contenu = part.getContent();
            return contenu != null ? contenu.toString() : "";
        }

        if (part.isMimeType("text/html")) {
            Object contenu = part.getContent();
            return contenu != null ? nettoyerHtml(contenu.toString()) : "";
        }

        if (part.isMimeType("multipart/*")) {
            Object contenu = part.getContent();

            if (contenu instanceof Multipart multipart) {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);

                    String disposition = bodyPart.getDisposition();

                    if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
                        continue;
                    }

                    String texte = extraireTexte(bodyPart);

                    if (texte != null && !texte.isBlank()) {
                        builder.append(texte).append("\n");
                    }
                }

                return builder.toString();
            }
        }

        return "";
    }

    private String nettoyerHtml(String html) {
        return html
                .replaceAll("(?i)<br\\s*/?>", "\n")
                .replaceAll("(?i)</p>", "\n")
                .replaceAll("<[^>]*>", " ")
                .replace("&nbsp;", " ")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private String creerApercu(String contenu) {
        if (contenu == null || contenu.isBlank()) {
            return "";
        }

        String propre = contenu.replaceAll("\\s+", " ").trim();

        if (propre.length() <= 300) {
            return propre;
        }

        return propre.substring(0, 300) + "...";
    }

    private void fermerStore(Store store) {
        if (store != null && store.isConnected()) {
            try {
                store.close();
            } catch (MessagingException ignored) {
            }
        }
    }
}