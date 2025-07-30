package com.sadou.archivage.infrastructure.converter;

import com.sadou.archivage.domain.valueobject.DateArchivage;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;

@Converter
public class DateArchivageConverter implements AttributeConverter<DateArchivage, LocalDateTime> {
    @Override
    public LocalDateTime convertToDatabaseColumn(DateArchivage attribute) {
        return attribute != null ? attribute.getValeur() : null;
    }

    @Override
    public DateArchivage convertToEntityAttribute(LocalDateTime dbData) {
        return dbData != null ? new DateArchivage(dbData) : null;
    }
} 