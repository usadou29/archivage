package com.sadou.archivage.application.port.inbound;

import com.sadou.archivage.domain.entity.Document;

import java.time.LocalDateTime;
import java.util.List;

public interface RechercherDocumentsPort {
    List<Document> rechercherDocuments(String auteur, LocalDateTime dateLimite);
} 