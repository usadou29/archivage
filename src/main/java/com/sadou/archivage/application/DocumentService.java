package com.sadou.archivage.application;

import com.sadou.archivage.domain.Document;
import com.sadou.archivage.domain.User;
import com.sadou.archivage.infrastructure.DocumentRepository;
import com.sadou.archivage.infrastructure.specification.DocumentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Document> rechercherDocuments(String auteur, LocalDateTime dateLimite) {
        return documentRepository.findAll(DocumentSpecification.searchByAuteurAndDate(auteur, dateLimite));
    }
}
