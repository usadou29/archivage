package com.sadou.archivage.bdd;

import com.sadou.archivage.application.DocumentService;
import com.sadou.archivage.domain.User;
import com.sadou.archivage.domain.Document;
import com.sadou.archivage.infrastructure.DocumentRepository;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonné;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ArchivageStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(ArchivageStepDefs.class);

    private User utilisateur;
    private Document documentSoumis;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepository documentRepository;

    @Étantdonné("un utilisateur authentifié avec le rôle {string}")
    public void un_utilisateur_authentifie_avec_le_role(String role) {
        utilisateur = new User();
        utilisateur.setRoles(Set.of(role));
        utilisateur.setUsername("test-user");
        logger.info("-------Step exécuté: utilisateur authentifié avec le rôle {}-------", role);
    }

    @Quand("il soumet un nouveau document PDF nommé {string}")
    public void il_soumet_un_nouveau_document_PDF_nomme(String nomFichier) {
        try {
            documentSoumis = documentService.archiverDocument(nomFichier, utilisateur);
            logger.info("-------Step exécuté: document soumis {}-------", nomFichier);
        } catch (Exception e) {
            logger.error("Erreur lors de l'archivage: {}", e.getMessage());
            throw e;
        }
    }

    @Alors("le document est archivé avec succès")
    public void le_document_est_archive_avec_succes() {
        assertThat(documentSoumis).isNotNull();
        logger.info("-------Step exécuté: document archivé avec succès-------");
    }

    @Et("les métadonnées du document sont enregistrées automatiquement")
    public void les_metadonnees_du_document_sont_enregistrees_automatiquement() {
        assertThat(documentSoumis.getNom()).isNotNull();
        assertThat(documentSoumis.getType()).isEqualTo("PDF");
        logger.info("-------Step exécuté: métadonnées enregistrées-------");
    }

    @Et("le document existe en base avec le nom {string}")
    public void le_document_existe_en_base_avec_le_nom(String nom) {
        Optional<Document> docOpt = documentRepository.findByNom(nom);
        assertThat(docOpt).isPresent();
        logger.info("-------Step exécuté: document trouvé en base-------");
    }
}