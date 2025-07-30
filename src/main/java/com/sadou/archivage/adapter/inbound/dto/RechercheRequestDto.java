package com.sadou.archivage.adapter.inbound.dto;

import java.time.LocalDateTime;

public class RechercheRequestDto {
    private String auteur;
    private LocalDateTime dateLimite;

    // Constructeurs
    public RechercheRequestDto() {}

    public RechercheRequestDto(String auteur, LocalDateTime dateLimite) {
        this.auteur = auteur;
        this.dateLimite = dateLimite;
    }

    // Getters et Setters
    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDateTime getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(LocalDateTime dateLimite) {
        this.dateLimite = dateLimite;
    }
} 