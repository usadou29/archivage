package com.sadou.archivage.bdd;

import com.sadou.archivage.application.DocumentService;
import com.sadou.archivage.domain.User;
import com.sadou.archivage.domain.Document;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonné;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchivageStepDefs {

    private User utilisateur;
    private Document documentSoumis;
    private DocumentService documentService = new DocumentService();

    @Étantdonné("un utilisateur authentifié avec le rôle {string}")
    public void un_utilisateur_authentifie_avec_le_role(String role) {
        utilisateur = new User();
        utilisateur.setRoles(Set.of(role));
    }

    @Quand("il soumet un nouveau document PDF nommé {string}")
    public void il_soumet_un_nouveau_document_PDF_nomme(String nomFichier) {
        documentSoumis = documentService.archiverDocument(nomFichier, utilisateur);
    }

    @Alors("le document est archivé avec succès")
    public void le_document_est_archive_avec_succes() {
        assertThat(documentSoumis).isNotNull();
    }

    @Et("les métadonnées du document sont enregistrées automatiquement")
    public void les_metadonnees_du_document_sont_enregistrees_automatiquement() {
        assertThat(documentSoumis.getNom()).isNotNull();
        assertThat(documentSoumis.getType()).isEqualTo("PDF");
    }
}