package com.sadou.archivage.domain.valueobject;

import java.util.Objects;

public class DocumentNom {
    private final String valeur;

    public DocumentNom(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du document ne peut pas être vide");
        }
        if (valeur.length() > 255) {
            throw new IllegalArgumentException("Le nom du document ne peut pas dépasser 255 caractères");
        }
        this.valeur = valeur.trim();
    }

    public String getValeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentNom that = (DocumentNom) o;
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