package com.sadou.archivage.adapter.inbound.dto;

import java.time.LocalDateTime;

public class DocumentDto {
    private String id;
    private String nom;
    private String type;
    private String auteur;
    private LocalDateTime dateArchivage;

    // Constructeurs
    public DocumentDto() {}

    public DocumentDto(String nom, String type, String auteur) {
        this.nom = nom;
        this.type = type;
        this.auteur = auteur;
        this.dateArchivage = LocalDateTime.now();
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDateTime getDateArchivage() {
        return dateArchivage;
    }

    public void setDateArchivage(LocalDateTime dateArchivage) {
        this.dateArchivage = dateArchivage;
    }
} 