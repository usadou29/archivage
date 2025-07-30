package com.sadou.archivage.infrastructure.persistence.converter;

import com.sadou.archivage.domain.valueobject.DocumentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DocumentTypeConverter implements AttributeConverter<DocumentType, String> {
    @Override
    public String convertToDatabaseColumn(DocumentType attribute) {
        return attribute != null ? attribute.getValeur() : null;
    }

    @Override
    public DocumentType convertToEntityAttribute(String dbData) {
        return dbData != null ? new DocumentType(dbData) : null;
    }
} 