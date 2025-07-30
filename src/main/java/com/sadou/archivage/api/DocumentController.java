package com.sadou.archivage.api;

import com.sadou.archivage.application.DocumentService;
import com.sadou.archivage.domain.Document;
import com.sadou.archivage.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<Document> archiverDocument(@RequestBody Document document) {
        // Pour simplifier, on crée un utilisateur temporaire
        // En production, on récupérerait l'utilisateur depuis le contexte de sécurité
        User utilisateur = new User();
        utilisateur.setUsername(document.getAuteurString() != null ? document.getAuteurString() : "default-user");
        
        Document documentArchive = documentService.archiverDocument(document.getNomString(), utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentArchive);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Document>> rechercherDocuments(
            @RequestParam String auteur,
            @RequestParam String dateLimite) {
        
        LocalDateTime dateLimiteParsed = LocalDateTime.parse(dateLimite + "T00:00:00");
        
        List<Document> documents = documentService.rechercherDocuments(auteur, dateLimiteParsed);
        
        return ResponseEntity.ok(documents);
    }
} 