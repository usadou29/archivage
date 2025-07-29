package com.sadou.archivage.application;

import com.sadou.archivage.domain.Document;
import com.sadou.archivage.domain.User;
import com.sadou.archivage.infrastructure.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document archiverDocument(String nomFichier, User utilisateur) {
        Document document = new Document();
        document.setNom(nomFichier);
        document.setType("PDF");
        document.setAuteur(utilisateur.getUsername());
        document.setDateArchivage(LocalDateTime.now());
        return documentRepository.save(document);
    }
}
