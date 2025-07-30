package com.sadou.archivage.infrastructure.persistence.converter;

import com.sadou.archivage.domain.valueobject.DocumentNom;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DocumentNomConverter implements AttributeConverter<DocumentNom, String> {
    @Override
    public String convertToDatabaseColumn(DocumentNom attribute) {
        return attribute != null ? attribute.getValeur() : null;
    }

    @Override
    public DocumentNom convertToEntityAttribute(String dbData) {
        return dbData != null ? new DocumentNom(dbData) : null;
    }
} 