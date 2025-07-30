package com.sadou.archivage.domain.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;

public class DateArchivage {
    private final LocalDateTime valeur;

    public DateArchivage(LocalDateTime valeur) {
        if (valeur == null) {
            throw new IllegalArgumentException("La date d'archivage ne peut pas être nulle");
        }
        if (valeur.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date d'archivage ne peut pas être dans le futur");
        }
        this.valeur = valeur;
    }

    public LocalDateTime getValeur() {
        return valeur;
    }

    public boolean estApres(DateArchivage autre) {
        return this.valeur.isAfter(autre.valeur);
    }

    public boolean estAvant(DateArchivage autre) {
        return this.valeur.isBefore(autre.valeur);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateArchivage that = (DateArchivage) o;
        return Objects.equals(valeur, that.valeur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valeur);
    }

    @Override
    public String toString() {
        return valeur.toString();
    }
} 