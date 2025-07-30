package com.sadou.archivage.adapter.inbound.mapper;

import com.sadou.archivage.adapter.inbound.dto.DocumentDto;
import com.sadou.archivage.domain.entity.Document;
import org.springframework.stereotype.Component;

@Component("inboundDocumentMapper")
public class DocumentMapper {
    
    /**
     * Convertit un DocumentDto en entité Document de domaine
     */
    public Document toDomainEntity(DocumentDto dto) {
        if (dto == null) {
            return null;
        }
        
        Document document = Document.creer(dto.getNom(), dto.getType(), dto.getAuteur());
        if (dto.getId() != null) {
            // Note: L'ID est géré par JPA, on ne le définit que si nécessaire
        }
        if (dto.getDateArchivage() != null) {
            document.setDateArchivage(new com.sadou.archivage.domain.valueobject.DateArchivage(dto.getDateArchivage()));
        }
        
        return document;
    }
    
    /**
     * Convertit une entité Document de domaine en DocumentDto
     */
    public DocumentDto toDto(Document document) {
        if (document == null) {
            return null;
        }
        
        DocumentDto dto = new DocumentDto();
        dto.setId(document.getId());
        dto.setNom(document.getNomString());
        dto.setType(document.getTypeString());
        dto.setAuteur(document.getAuteurString());
        dto.setDateArchivage(document.getDateArchivageLocalDateTime());
        
        return dto;
    }
} 