# Exemple d'Utilisation de la Validation dans l'Architecture Hexagonale

## Vue d'ensemble

La validation des données d'entrée est implémentée dans la couche **Adapter Inbound** selon les principes de l'architecture hexagonale. Cela garantit que seules des données valides atteignent le domaine métier.

## Structure de Validation

### 1. Validateur (`DocumentValidator`)
```java
@Component
public class DocumentValidator implements Validator {
    @Override
    public void validate(Object target, Errors errors) {
        DocumentDto documentDto = (DocumentDto) target;
        
        // Validation du nom
        if (documentDto.getNom() == null || documentDto.getNom().trim().isEmpty()) {
            errors.rejectValue("nom", "NotEmpty", "Le nom du document est obligatoire");
        }
        
        // Validation de l'auteur
        if (documentDto.getAuteur() == null || documentDto.getAuteur().trim().isEmpty()) {
            errors.rejectValue("auteur", "NotEmpty", "L'auteur est obligatoire");
        }
        
        // Validation du type
        if (documentDto.getType() == null || documentDto.getType().trim().isEmpty()) {
            errors.rejectValue("type", "NotEmpty", "Le type de document est obligatoire");
        }
        
        // Validation du format du nom de fichier
        if (documentDto.getNom() != null && !documentDto.getNom().contains(".")) {
            errors.rejectValue("nom", "InvalidFormat", "Le nom du fichier doit contenir une extension");
        }
    }
}
```

### 2. DTO d'Erreur (`ValidationErrorDto`)
```java
public class ValidationErrorDto {
    private String message;
    private Map<String, List<String>> fieldErrors;
    private String timestamp;
}
```

### 3. Contrôleur avec Validation
```java
@PostMapping
public ResponseEntity<?> archiverDocument(@RequestBody DocumentDto documentDto, BindingResult bindingResult) {
    // Valider les données d'entrée
    documentValidator.validate(documentDto, bindingResult);
    
    if (bindingResult.hasErrors()) {
        // Créer un DTO d'erreur de validation
        Map<String, List<String>> fieldErrors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
        
        ValidationErrorDto errorDto = new ValidationErrorDto(
                "Erreur de validation des données", 
                fieldErrors
        );
        
        return ResponseEntity.badRequest().body(errorDto);
    }
    
    // Traitement normal...
}
```

## Exemples d'Utilisation

### ✅ Requête Valide
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "rapport.pdf",
    "type": "PDF",
    "auteur": "John Doe"
  }'
```

**Réponse :**
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "nom": "rapport.pdf",
  "type": "PDF",
  "auteur": "John Doe",
  "dateArchivage": "2025-07-30T03:30:15.123"
}
```

### ❌ Requête Invalide (Nom manquant)
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "type": "PDF",
    "auteur": "John Doe"
  }'
```

**Réponse :**
```json
{
  "message": "Erreur de validation des données",
  "fieldErrors": {
    "nom": ["Le nom du document est obligatoire"]
  },
  "timestamp": "2025-07-30T03:30:15.123"
}
```

### ❌ Requête Invalide (Auteur manquant)
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "rapport.pdf",
    "type": "PDF"
  }'
```

**Réponse :**
```json
{
  "message": "Erreur de validation des données",
  "fieldErrors": {
    "auteur": ["L'auteur est obligatoire"]
  },
  "timestamp": "2025-07-30T03:30:15.123"
}
```

### ❌ Requête Invalide (Format de nom incorrect)
```bash
curl -X POST http://localhost:8080/api/documents \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "rapport",
    "type": "PDF",
    "auteur": "John Doe"
  }'
```

**Réponse :**
```json
{
  "message": "Erreur de validation des données",
  "fieldErrors": {
    "nom": ["Le nom du fichier doit contenir une extension"]
  },
  "timestamp": "2025-07-30T03:30:15.123"
}
```

## Avantages de cette Approche

### 1. **Séparation des Responsabilités**
- La validation est dans la couche adapter inbound
- Le domaine reste pur et ne connaît pas la validation des DTOs

### 2. **Messages d'Erreur Détaillés**
- Chaque champ invalide est identifié
- Messages d'erreur explicites pour l'utilisateur

### 3. **Cohérence avec l'Architecture Hexagonale**
- Validation avant l'entrée dans le domaine
- DTOs d'erreur spécifiques à la couche adapter

### 4. **Testabilité**
- Validation testable indépendamment
- Tests unitaires pour chaque règle de validation

## Tests de Validation

```java
@Test
void archiverDocument_WithInvalidData_ShouldReturnBadRequest() throws Exception {
    DocumentDto invalidDto = new DocumentDto(); // DTO vide
    
    mockMvc.perform(post("/api/documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest());
}
```

## Évolutions Possibles

1. **Validation par Annotations** : Utiliser `@Valid` et annotations JSR-303
2. **Validation Métier** : Ajouter des règles métier dans le service de domaine
3. **Validation Asynchrone** : Validation en arrière-plan pour les gros fichiers
4. **Validation Contextuelle** : Règles différentes selon le contexte (admin vs utilisateur) 