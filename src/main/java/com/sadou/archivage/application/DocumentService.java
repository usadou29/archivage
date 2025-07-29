package com.sadou.archivage.application;

import com.sadou.archivage.domain.Document;
import com.sadou.archivage.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentService {
    public Document archiverDocument(String nom, User user) {
        Document doc = new Document();
        doc.setNom(nom);
        doc.setType("PDF");
        doc.setAuteur(user.getUsername());
        doc.setDateArchivage(LocalDateTime.now());
        // Pas de persistence, juste retour
        return doc;
    }
}
