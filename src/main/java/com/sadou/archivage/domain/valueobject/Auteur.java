package com.sadou.archivage.domain.valueobject;

import java.util.Objects;

public class Auteur {
    private final String valeur;

    public Auteur(String valeur) {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }
        if (valeur.length() > 100) {
            throw new IllegalArgumentException("Le nom de l'auteur ne peut pas dépasser 100 caractères");
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
        Auteur auteur = (Auteur) o;
        return Objects.equals(valeur, auteur.valeur);
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