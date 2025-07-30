package com.sadou.archivage.application.port;

import com.sadou.archivage.domain.entity.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    Document save(Document document);
    Optional<Document> findByNom(String nom);
    List<Document> findAll();
    List<Document> findByAuteurAndDateAfter(String auteur, LocalDateTime dateLimite);
    long count();
} 