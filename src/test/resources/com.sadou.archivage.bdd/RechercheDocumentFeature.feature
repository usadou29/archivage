Feature: Recherche avancée de documents

  Scenario: Rechercher un document par auteur et date
    Given un utilisateur authentifié avec le rôle "VIEWER"
    And plusieurs documents archivés existent
    When il recherche les documents de l'auteur "John Doe" créés après le "2025-01-01"
    Then il obtient une liste de documents correspondants

  Scenario: Rechercher des documents via l'API REST
    Given un utilisateur authentifié avec le rôle "VIEWER"
    And plusieurs documents archivés existent
    When il envoie une requête GET à "/api/documents/search" avec auteur "John Doe" et date "2025-01-01"
    Then la recherche API retourne des résultats
    And il obtient une liste de documents via l'API 