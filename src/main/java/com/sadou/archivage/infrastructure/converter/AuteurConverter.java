package com.sadou.archivage.infrastructure.converter;

import com.sadou.archivage.domain.valueobject.Auteur;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AuteurConverter implements AttributeConverter<Auteur, String> {
    @Override
    public String convertToDatabaseColumn(Auteur attribute) {
        return attribute != null ? attribute.getValeur() : null;
    }

    @Override
    public Auteur convertToEntityAttribute(String dbData) {
        return dbData != null ? new Auteur(dbData) : null;
    }
} 