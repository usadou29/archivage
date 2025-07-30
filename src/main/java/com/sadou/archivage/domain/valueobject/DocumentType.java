package com.sadou.archivage.domain.valueobject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DocumentType {
    private final String valeur;
    
    private static final List<String> TYPES_AUTORISES = Arrays.asList("PDF", "DOC", "DOCX", "TXT", "XLS", "XLSX");

    public DocumentType(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de document ne peut pas être vide");
        }
        String typeNormalise = valeur.trim().toUpperCase();
        if (!TYPES_AUTORISES.contains(typeNormalise)) {
            throw new IllegalArgumentException("Type de document non autorisé: " + valeur + 
                ". Types autorisés: " + TYPES_AUTORISES);
        }
        this.valeur = typeNormalise;
    }

    public String getValeur() {
        return valeur;
    }

    public static List<String> getTypesAutorises() {
        return List.copyOf(TYPES_AUTORISES);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentType that = (DocumentType) o;
        return Objects.equals(valeur, that.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

    @Override
    public String toString() {
        return valeur;
    }
} 