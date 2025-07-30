package com.sadou.archivage.application.usecase;

import com.sadou.archivage.application.port.DocumentRepository;
import com.sadou.archivage.application.port.inbound.RechercherDocumentsPort;
import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.domain.service.DocumentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RechercherDocumentsUseCase implements RechercherDocumentsPort {
    
    private final DocumentRepository documentRepository;
    private final DocumentService documentService;
    
    public RechercherDocumentsUseCase(DocumentRepository documentRepository, DocumentService documentService) {
        this.documentRepository = documentRepository;
        this.documentService = documentService;
    }
    
    @Override
    public List<Document> rechercherDocuments(String auteur, LocalDateTime dateLimite) {
        // Récupérer tous les documents
        List<Document> tousLesDocuments = documentRepository.findAll();
        
        // Utiliser le service de domaine pour filtrer
        return documentService.filtrerDocuments(tousLesDocuments, auteur, dateLimite);
    }
} 