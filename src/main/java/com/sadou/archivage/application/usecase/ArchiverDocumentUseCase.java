package com.sadou.archivage.application.usecase;

import com.sadou.archivage.application.port.DocumentRepository;
import com.sadou.archivage.application.port.inbound.ArchiverDocumentPort;
import com.sadou.archivage.domain.entity.User;
import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.domain.service.DocumentService;
import org.springframework.stereotype.Service;

@Service
public class ArchiverDocumentUseCase implements ArchiverDocumentPort {
    
    private final DocumentRepository documentRepository;
    private final DocumentService documentService;
    
    public ArchiverDocumentUseCase(DocumentRepository documentRepository, DocumentService documentService) {
        this.documentRepository = documentRepository;
        this.documentService = documentService;
    }
    
    @Override
    public Document archiverDocument(String nomFichier, User utilisateur) {
        // Déterminer le type de document basé sur l'extension
        String typeDocument = documentService.determinerTypeDocument(nomFichier);
        
        // Créer le document
        Document document = Document.creer(nomFichier, typeDocument, utilisateur.getUsername());
        
        // Valider le document avant archivage
        if (!documentService.peutEtreArchive(document)) {
            throw new IllegalArgumentException("Le document ne peut pas être archivé : données invalides");
        }
        
        return documentRepository.save(document);
    }
} 