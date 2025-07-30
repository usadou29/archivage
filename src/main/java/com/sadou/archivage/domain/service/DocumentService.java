package com.sadou.archivage.domain.service;

import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.domain.valueobject.DocumentNom;
import com.sadou.archivage.domain.valueobject.DocumentType;
import com.sadou.archivage.domain.valueobject.Auteur;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentService {
    
    /**
     * Valide si un document peut être archivé
     */
    public boolean peutEtreArchive(Document document) {
        if (document == null) {
            return false;
        }
        
        // Vérifier que le nom du document n'est pas vide
        if (document.getNomString() == null || document.getNomString().trim().isEmpty()) {
            return false;
        }
        
        // Vérifier que l'auteur est spécifié
        if (document.getAuteurString() == null || document.getAuteurString().trim().isEmpty()) {
            return false;
        }
        
        // Vérifier que le type de document est valide
        if (document.getTypeString() == null || document.getTypeString().trim().isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Filtre les documents selon les critères de recherche
     */
    public List<Document> filtrerDocuments(List<Document> documents, String auteur, LocalDateTime dateLimite) {
        return documents.stream()
                .filter(doc -> doc.getAuteurString().equals(auteur))
                .filter(doc -> doc.getDateArchivageLocalDateTime().isAfter(dateLimite))
                .collect(Collectors.toList());
    }
    
    /**
     * Détermine le type de document basé sur l'extension du fichier
     */
    public String determinerTypeDocument(String nomFichier) {
        if (nomFichier == null || nomFichier.trim().isEmpty()) {
            return "UNKNOWN";
        }
        
        String extension = nomFichier.substring(nomFichier.lastIndexOf(".") + 1).toUpperCase();
        
        switch (extension) {
            case "PDF":
                return "PDF";
            case "DOC":
            case "DOCX":
                return "WORD";
            case "XLS":
            case "XLSX":
                return "EXCEL";
            case "PPT":
            case "PPTX":
                return "POWERPOINT";
            case "TXT":
                return "TEXT";
            case "JPG":
            case "JPEG":
            case "PNG":
                return "IMAGE";
            default:
                return "OTHER";
        }
    }
} 