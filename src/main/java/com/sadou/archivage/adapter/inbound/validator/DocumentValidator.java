package com.sadou.archivage.adapter.inbound.validator;

import com.sadou.archivage.adapter.inbound.dto.DocumentDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validateur pour les documents dans l'architecture hexagonale
 */
@Component
public class DocumentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DocumentDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DocumentDto documentDto = (DocumentDto) target;
        
        // Valider le nom du document
        if (documentDto.getNom() == null || documentDto.getNom().trim().isEmpty()) {
            errors.rejectValue("nom", "NotEmpty", "Le nom du document est obligatoire");
        }
        
        // Valider l'auteur
        if (documentDto.getAuteur() == null || documentDto.getAuteur().trim().isEmpty()) {
            errors.rejectValue("auteur", "NotEmpty", "L'auteur est obligatoire");
        }
        
        // Valider le type de document
        if (documentDto.getType() == null || documentDto.getType().trim().isEmpty()) {
            errors.rejectValue("type", "NotEmpty", "Le type de document est obligatoire");
        }
        
        // Valider le format du nom de fichier
        if (documentDto.getNom() != null && !documentDto.getNom().contains(".")) {
            errors.rejectValue("nom", "InvalidFormat", "Le nom du fichier doit contenir une extension");
        }
    }
} 