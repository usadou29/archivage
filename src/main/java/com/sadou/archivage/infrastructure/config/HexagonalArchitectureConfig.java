package com.sadou.archivage.infrastructure.config;

import com.sadou.archivage.adapter.inbound.mapper.DocumentMapper;
import com.sadou.archivage.domain.service.DocumentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour l'architecture hexagonale
 * Cette classe configure les composants de l'architecture hexagonale
 */
@Configuration
public class HexagonalArchitectureConfig {

    /**
     * Bean pour le service de domaine DocumentService
     */
    @Bean
    public DocumentService documentService() {
        return new DocumentService();
    }

    /**
     * Bean pour le mapper des documents inbound
     */
    @Bean("inboundDocumentMapper")
    public DocumentMapper inboundDocumentMapper() {
        return new DocumentMapper();
    }
} 