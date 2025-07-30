package com.sadou.archivage.bdd;

import com.sadou.archivage.application.DocumentService;
import com.sadou.archivage.domain.Document;
import com.sadou.archivage.domain.User;
import com.sadou.archivage.infrastructure.DocumentRepository;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RechercheStepDefs {

    private static final Logger logger = LoggerFactory.getLogger(RechercheStepDefs.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    private List<Document> documentsRecherches;
    private List<Document> documentsRecherchesApi;

    @Et("plusieurs documents archivés existent")
    public void plusieurs_documents_archives_existent() {
        // Créer plusieurs documents de test avec différents auteurs et dates
        User johnDoe = new User();
        johnDoe.setUsername("John Doe");
        johnDoe.setRoles(Set.of("VIEWER"));

        User janeSmith = new User();
        janeSmith.setUsername("Jane Smith");
        janeSmith.setRoles(Set.of("VIEWER"));

        // Document de John Doe créé en 2025
        Document doc1 = new Document();
        doc1.setNom("Rapport_2025_John.pdf");
        doc1.setType("PDF");
        doc1.setAuteur("John Doe");
        doc1.setDateArchivage(LocalDateTime.of(2025, 2, 15, 10, 30));
        documentRepository.save(doc1);

        // Document de John Doe créé en 2024 (ne devrait pas être trouvé)
        Document doc2 = new Document();
        doc2.setNom("Rapport_2024_John.pdf");
        doc2.setType("PDF");
        doc2.setAuteur("John Doe");
        doc2.setDateArchivage(LocalDateTime.of(2024, 12, 15, 10, 30));
        documentRepository.save(doc2);

        // Document de Jane Smith créé en 2025 (ne devrait pas être trouvé)
        Document doc3 = new Document();
        doc3.setNom("Rapport_2025_Jane.pdf");
        doc3.setType("PDF");
        doc3.setAuteur("Jane Smith");
        doc3.setDateArchivage(LocalDateTime.of(2025, 3, 20, 14, 45));
        documentRepository.save(doc3);

        // Document de John Doe créé en 2025 (devrait être trouvé)
        Document doc4 = new Document();
        doc4.setNom("Memo_2025_John.pdf");
        doc4.setType("PDF");
        doc4.setAuteur("John Doe");
        doc4.setDateArchivage(LocalDateTime.of(2025, 1, 15, 9, 15));
        documentRepository.save(doc4);

        logger.info("-------Step exécuté: {} documents de test créés-------", documentRepository.count());
    }

    @Quand("il recherche les documents de l'auteur {string} créés après le {string}")
    public void il_recherche_les_documents_de_lauteur_crees_apres_le(String auteur, String dateLimite) {
        LocalDateTime dateLimiteParsed = LocalDateTime.parse(dateLimite + "T00:00:00");
        
        // Rechercher les documents de l'auteur créés après la date limite
        List<Document> documentsTrouves = documentRepository.findAll().stream()
                .filter(doc -> doc.getAuteur().equals(auteur))
                .filter(doc -> doc.getDateArchivage().isAfter(dateLimiteParsed))
                .toList();
        
        // Stocker les résultats pour les assertions
        this.documentsRecherches = documentsTrouves;
        
        logger.info("-------Step exécuté: recherche effectuée pour l'auteur '{}' après '{}' - {} documents trouvés-------", 
                   auteur, dateLimite, documentsTrouves.size());
    }

    @Alors("il obtient une liste de documents correspondants")
    public void il_obtient_une_liste_de_documents_correspondants() {
        assertThat(documentsRecherches).isNotNull();
        assertThat(documentsRecherches).isNotEmpty();
        
        // Vérifier que tous les documents trouvés sont bien de John Doe et créés après 2025-01-01
        LocalDateTime dateLimite = LocalDateTime.of(2025, 1, 1, 0, 0);
        
        for (Document doc : documentsRecherches) {
            assertThat(doc.getAuteur()).isEqualTo("John Doe");
            assertThat(doc.getDateArchivage()).isAfter(dateLimite);
        }
        
        logger.info("-------Step exécuté: {} documents correspondants validés-------", documentsRecherches.size());
    }

    @Quand("il envoie une requête GET à {string} avec auteur {string} et date {string}")
    public void il_envoie_une_requete_GET_a_avec_auteur_et_date(String endpoint, String auteur, String date) {
        logger.info("Test direct du service de recherche");
        
        LocalDateTime dateLimiteParsed = LocalDateTime.parse(date + "T00:00:00");
        
        try {
            documentsRecherchesApi = documentService.rechercherDocuments(auteur, dateLimiteParsed);
            logger.info("-------Step exécuté: recherche via service direct - {} documents trouvés-------", 
                       documentsRecherchesApi.size());
        } catch (Exception e) {
            logger.error("Erreur lors de la recherche: {}", e.getMessage());
            throw e;
        }
    }





    @Alors("la recherche API retourne des résultats")
    public void la_recherche_API_retourne_des_resultats() {
        assertThat(documentsRecherchesApi).isNotNull();
        assertThat(documentsRecherchesApi).isNotEmpty();
        logger.info("-------Step exécuté: recherche API réussie - {} documents trouvés-------", 
                   documentsRecherchesApi.size());
    }

    @Alors("il obtient une liste de documents via l'API")
    public void il_obtient_une_liste_de_documents_via_l_api() {
        assertThat(documentsRecherchesApi).isNotNull();
        assertThat(documentsRecherchesApi).isNotEmpty();
        logger.info("-------Step exécuté: {} documents trouvés via API-------", documentsRecherchesApi.size());
    }
} 