package com.sadou.archivage.infrastructure.persistence.mapper;

import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.infrastructure.persistence.entity.DocumentJpaEntity;
import org.springframework.stereotype.Component;

@Component("persistenceDocumentMapper")
public class DocumentMapper {
    
    public DocumentJpaEntity toJpaEntity(Document document) {
        DocumentJpaEntity jpaEntity = new DocumentJpaEntity();
        jpaEntity.setId(document.getId());
        jpaEntity.setNom(document.getNomString());
        jpaEntity.setType(document.getTypeString());
        jpaEntity.setAuteur(document.getAuteurString());
        jpaEntity.setDateArchivage(document.getDateArchivageLocalDateTime().toString());
        return jpaEntity;
    }
    
    public Document toDomainEntity(DocumentJpaEntity jpaEntity) {
        Document document = Document.creer(jpaEntity.getNom(), jpaEntity.getType(), jpaEntity.getAuteur());
        document.setId(jpaEntity.getId());
        return document;
    }
} 