package com.sadou.archivage.domain.entity;

import com.sadou.archivage.domain.valueobject.Auteur;
import com.sadou.archivage.domain.valueobject.DateArchivage;
import com.sadou.archivage.domain.valueobject.DocumentNom;
import com.sadou.archivage.domain.valueobject.DocumentType;
import com.sadou.archivage.infrastructure.persistence.converter.AuteurConverter;
import com.sadou.archivage.infrastructure.persistence.converter.DateArchivageConverter;
import com.sadou.archivage.infrastructure.persistence.converter.DocumentNomConverter;
import com.sadou.archivage.infrastructure.persistence.converter.DocumentTypeConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class Document {
    
    // Constructeur pour créer un nouveau document
    public static Document creer(String nom, String type, String auteur) {
        Document document = new Document();
        document.nom = new DocumentNom(nom);
        document.type = new DocumentType(type);
        document.auteur = new Auteur(auteur);
        document.dateArchivage = new DateArchivage(LocalDateTime.now());
        return document;
    }
    
    // Méthodes utilitaires pour accéder aux valeurs
    public String getNomString() {
        return nom != null ? nom.getValeur() : null;
    }
    
    public String getTypeString() {
        return type != null ? type.getValeur() : null;
    }
    
    public String getAuteurString() {
        return auteur != null ? auteur.getValeur() : null;
    }
    
    public LocalDateTime getDateArchivageLocalDateTime() {
        return dateArchivage != null ? dateArchivage.getValeur() : null;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    @Convert(converter = DocumentNomConverter.class)
    private DocumentNom nom;
    
    @Column(nullable = false)
    @Convert(converter = DocumentTypeConverter.class)
    private DocumentType type;
    
    @Column(name = "date_archivage")
    @Convert(converter = DateArchivageConverter.class)
    private DateArchivage dateArchivage;
    
    @Column(name = "auteur")
    @Convert(converter = AuteurConverter.class)
    private Auteur auteur;
}
