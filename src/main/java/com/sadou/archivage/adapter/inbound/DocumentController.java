package com.sadou.archivage.adapter.inbound;

import com.sadou.archivage.adapter.inbound.dto.DocumentDto;
import com.sadou.archivage.adapter.inbound.dto.RechercheRequestDto;
import com.sadou.archivage.adapter.inbound.mapper.DocumentMapper;
import com.sadou.archivage.application.port.inbound.ArchiverDocumentPort;
import com.sadou.archivage.application.port.inbound.RechercherDocumentsPort;
import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.domain.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final ArchiverDocumentPort archiverDocumentPort;
    private final RechercherDocumentsPort rechercherDocumentsPort;
    private final DocumentMapper documentMapper;

    public DocumentController(ArchiverDocumentPort archiverDocumentPort, 
                           RechercherDocumentsPort rechercherDocumentsPort,
                           @Qualifier("inboundDocumentMapper") DocumentMapper documentMapper) {
        this.archiverDocumentPort = archiverDocumentPort;
        this.rechercherDocumentsPort = rechercherDocumentsPort;
        this.documentMapper = documentMapper;
    }

    @PostMapping
    public ResponseEntity<DocumentDto> archiverDocument(@RequestBody DocumentDto documentDto) {
        // Convertir le DTO en entité de domaine
        Document document = documentMapper.toDomainEntity(documentDto);
        
        // Créer un utilisateur temporaire (en production, récupérer depuis le contexte de sécurité)
        User utilisateur = new User();
        utilisateur.setUsername(document.getAuteurString() != null ? document.getAuteurString() : "default-user");
        
        // Archiver le document via le port
        Document documentArchive = archiverDocumentPort.archiverDocument(document.getNomString(), utilisateur);
        
        // Convertir le résultat en DTO
        DocumentDto resultDto = documentMapper.toDto(documentArchive);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DocumentDto>> rechercherDocuments(
            @RequestParam String auteur,
            @RequestParam String dateLimite) {
        
        LocalDateTime dateLimiteParsed = LocalDateTime.parse(dateLimite + "T00:00:00");
        
        // Rechercher les documents via le port
        List<Document> documents = rechercherDocumentsPort.rechercherDocuments(auteur, dateLimiteParsed);
        
        // Convertir les résultats en DTOs
        List<DocumentDto> documentDtos = documents.stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(documentDtos);
    }
} 