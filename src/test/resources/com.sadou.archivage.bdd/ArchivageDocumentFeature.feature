Feature: Archivage d'un document

  Scenario: Archiver un nouveau document PDF
    Given un utilisateur authentifié avec le rôle "EDITOR"
    When il soumet un nouveau document PDF nommé "Rapport_annuel.pdf"
    Then le document est archivé avec succès
    And les métadonnées du document sont enregistrées automatiquement
    And le document existe en base avec le nom "Rapport_annuel.pdf"