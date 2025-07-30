# Architecture Hexagonale - Système d'Archivage

## Vue d'ensemble

Ce projet implémente l'architecture hexagonale (Clean Architecture) pour un système d'archivage de documents. L'architecture est organisée en couches bien définies avec des dépendances dirigées vers le centre (domaine).

## Structure de l'Architecture

```
src/main/java/com/sadou/archivage/
├── domain/                    # Cœur métier (Domain)
│   ├── entity/               # Entités de domaine
│   │   ├── Document.java
│   │   └── User.java
│   ├── valueobject/          # Objets de valeur
│   │   ├── Auteur.java
│   │   ├── DateArchivage.java
│   │   ├── DocumentNom.java
│   │   └── DocumentType.java
│   └── service/              # Services de domaine
│       └── DocumentService.java
├── application/              # Couche application
│   ├── port/                # Ports (interfaces)
│   │   ├── inbound/         # Ports d'entrée
│   │   │   ├── ArchiverDocumentPort.java
│   │   │   └── RechercherDocumentsPort.java
│   │   └── outbound/        # Ports de sortie
│   │       └── DocumentRepository.java
│   └── usecase/             # Cas d'usage
│       ├── ArchiverDocumentUseCase.java
│       └── RechercherDocumentsUseCase.java
├── adapter/                 # Adaptateurs
│   ├── inbound/             # Adaptateurs d'entrée
│   │   ├── DocumentController.java
│   │   ├── dto/             # DTOs
│   │   │   ├── DocumentDto.java
│   │   │   └── RechercheRequestDto.java
│   │   ├── mapper/          # Mappers
│   │   │   └── DocumentMapper.java
│   │   ├── validator/       # Validateurs
│   │   │   └── DocumentValidator.java
│   │   └── exception/       # Gestion d'erreurs
│   │       ├── GlobalExceptionHandler.java
│   │       └── ResourceNotFoundException.java
│   └── outbound/            # Adaptateurs de sortie
│       └── persistence/     # Persistence
│           ├── JpaDocumentRepository.java
│           ├── entity/
│           │   └── DocumentJpaEntity.java
│           ├── mapper/
│           │   └── DocumentMapper.java
│           └── converter/
│               ├── AuteurConverter.java
│               ├── DateArchivageConverter.java
│               ├── DocumentNomConverter.java
│               └── DocumentTypeConverter.java
└── infrastructure/          # Infrastructure
    ├── config/
    │   └── HexagonalArchitectureConfig.java
    └── security/            # Sécurité
        ├── SecurityConfig.java
        ├── JwtService.java
        └── CustomUserDetailsService.java
```

## Principes de l'Architecture Hexagonale

### 1. Domain (Cœur métier)
- **Entités** : `Document`, `User` - représentent les concepts métier
- **Value Objects** : `Auteur`, `DateArchivage`, `DocumentNom`, `DocumentType` - objets immutables
- **Services de domaine** : `DocumentService` - logique métier complexe

### 2. Application (Couche application)
- **Ports d'entrée** : Interfaces pour les cas d'usage
- **Ports de sortie** : Interfaces pour les repositories
- **Use Cases** : Orchestration des cas d'usage métier

### 3. Adapters (Adaptateurs)
- **Inbound** : Contrôleurs REST, DTOs, Mappers, Validateurs
- **Outbound** : Repositories JPA, Converters

## Flux de Données

### Archivage d'un Document
1. **Controller** reçoit une requête HTTP avec `DocumentDto`
2. **Mapper** convertit `DocumentDto` en entité `Document`
3. **Validator** valide les données d'entrée
4. **Use Case** orchestre l'archivage via le port
5. **Service de domaine** valide la logique métier
6. **Repository** persiste l'entité
7. **Mapper** convertit le résultat en `DocumentDto`
8. **Controller** retourne la réponse HTTP

### Recherche de Documents
1. **Controller** reçoit les paramètres de recherche
2. **Use Case** orchestre la recherche via le port
3. **Service de domaine** filtre les documents
4. **Repository** récupère les données
5. **Mapper** convertit les résultats en DTOs
6. **Controller** retourne la liste des documents

## Avantages de cette Architecture

### 1. Indépendance des Frameworks
- Le domaine ne dépend d'aucun framework
- Les adaptateurs peuvent être changés sans affecter le domaine

### 2. Testabilité
- Chaque couche peut être testée indépendamment
- Les mocks sont faciles à créer grâce aux ports

### 3. Flexibilité
- Possibilité de changer de base de données
- Possibilité d'ajouter de nouveaux adaptateurs (GraphQL, gRPC, etc.)

### 4. Séparation des Responsabilités
- Chaque couche a une responsabilité claire
- Les dépendances sont explicites et contrôlées

## Configuration Spring

La classe `HexagonalArchitectureConfig` configure les beans nécessaires à l'architecture hexagonale :

```java
@Configuration
public class HexagonalArchitectureConfig {
    @Bean
    public DocumentService documentService() {
        return new DocumentService();
    }
    
    @Bean
    public DocumentMapper documentMapper() {
        return new DocumentMapper();
    }
}
```

## Gestion d'Erreurs

L'architecture inclut une gestion d'erreurs centralisée :

- `GlobalExceptionHandler` : Gestionnaire global d'exceptions
- `ResourceNotFoundException` : Exception métier
- `DocumentValidator` : Validation des données d'entrée

## Tests

L'architecture facilite les tests grâce à :

- **Tests unitaires** : Chaque couche peut être testée isolément
- **Tests d'intégration** : Tests des adaptateurs
- **Tests BDD** : Tests Cucumber pour les scénarios métier

## Évolutions Futures

Cette architecture permet d'ajouter facilement :

1. **Nouveaux adaptateurs** : GraphQL, gRPC, WebSocket
2. **Nouveaux cas d'usage** : Export, Import, Archivage automatique
3. **Nouvelles technologies** : MongoDB, Redis, Elasticsearch
4. **Microservices** : Séparation en services indépendants 