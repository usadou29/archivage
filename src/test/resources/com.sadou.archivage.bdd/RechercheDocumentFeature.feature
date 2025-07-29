Feature: Recherche avancée de documents

  Scenario: Rechercher un document par auteur et date
    Given un utilisateur authentifié avec le rôle "VIEWER"
    And plusieurs documents archivés existent
    When il recherche les documents de l'auteur "John Doe" créés après le "2025-01-01"
    Then il obtient une liste de documents correspondants 