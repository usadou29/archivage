Feature: Archivage d'un document

  Scenario: Archiver un nouveau document PDF
    Given un utilisateur authentifié avec le rôle "EDITOR"
    When il soumet un nouveau document PDF nommé "Rapport_annuel.pdf"
    Then le document est archivé avec succès
    And les métadonnées du document sont enregistrées automatiquement
    And le document existe en base avec le nom "Rapport_annuel.pdf"

  Scenario: Archiver un document PDF via l'API REST
    Given un utilisateur authentifié avec le rôle "EDITOR"
    When il envoie une requête POST à "/api/documents" avec le document "Rapport_annuel.pdf"
    Then la réponse HTTP est "201"
    And le document existe en base avec le nom "Rapport_annuel.pdf"