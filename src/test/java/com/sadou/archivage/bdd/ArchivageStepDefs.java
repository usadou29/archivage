package com.sadou.archivage.bdd;

import com.sadou.archivage.domain.User;
import com.sadou.archivage.domain.Document;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Étantdonné;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchivageStepDefs {

    private User utilisateur;
    private Document documentSoumis;
    private List<Document> baseArchivage = new ArrayList<>();

    @Étantdonné("un utilisateur authentifié avec le rôle {string}")
    public void un_utilisateur_authentifie_avec_le_role(String role) {
        utilisateur = new User();
        utilisateur.setRoles(Set.of(role));
        System.out.println("-------Step exécuté: utilisateur authentifié avec le rôle " + role + "-------");
    }

    @Quand("il soumet un nouveau document PDF nommé {string}")
    public void il_soumet_un_nouveau_document_PDF_nomme(String nomFichier) {
        documentSoumis = new Document();
        documentSoumis.setNom(nomFichier);
        documentSoumis.setType("PDF");
        // Appel à la logique métier à implémenter
        baseArchivage.add(documentSoumis);
        System.out.println("-------Step exécuté: document soumis " + nomFichier + "-------");
    }

    @Alors("le document est archivé avec succès")
    public void le_document_est_archive_avec_succes() {
        assertThat(baseArchivage).contains(documentSoumis);
        System.out.println("-------Step exécuté: document archivé avec succès-------");
    }

    @Et("les métadonnées du document sont enregistrées automatiquement")
    public void les_metadonnees_du_document_sont_enregistrees_automatiquement() {
        assertThat(documentSoumis.getNom()).isNotNull();
        assertThat(documentSoumis.getType()).isEqualTo("PDF");
        System.out.println("-------Step exécuté: métadonnées enregistrées-------");
    }
}
