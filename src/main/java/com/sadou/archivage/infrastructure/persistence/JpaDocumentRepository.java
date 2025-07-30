package com.sadou.archivage.infrastructure.persistence;

import com.sadou.archivage.application.port.DocumentRepository;
import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.infrastructure.persistence.entity.DocumentJpaEntity;
import com.sadou.archivage.infrastructure.persistence.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaDocumentRepository implements DocumentRepository {
    
    private final DocumentJpaRepository jpaRepository;
    private final DocumentMapper mapper;
    
    public JpaDocumentRepository(DocumentJpaRepository jpaRepository, 
                               @Qualifier("persistenceDocumentMapper") DocumentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Document save(Document document) {
        DocumentJpaEntity jpaEntity = mapper.toJpaEntity(document);
        DocumentJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(savedEntity);
    }
    
    @Override
    public Optional<Document> findByNom(String nom) {
        return jpaRepository.findByNom(nom)
                .map(mapper::toDomainEntity);
    }
    
    @Override
    public List<Document> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomainEntity)
                .toList();
    }
    
    @Override
    public List<Document> findByAuteurAndDateAfter(String auteur, LocalDateTime dateLimite) {
        return jpaRepository.findByAuteurAndDateAfter(auteur, dateLimite).stream()
                .map(mapper::toDomainEntity)
                .toList();
    }
    
    @Override
    public long count() {
        return jpaRepository.count();
    }
} 