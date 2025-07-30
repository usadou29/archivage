package com.sadou.archivage.adapter.inbound;

import com.sadou.archivage.adapter.inbound.dto.DocumentDto;
import com.sadou.archivage.adapter.inbound.dto.ValidationErrorDto;
import com.sadou.archivage.adapter.inbound.mapper.DocumentMapper;
import com.sadou.archivage.adapter.inbound.validator.DocumentValidator;
import com.sadou.archivage.application.port.inbound.ArchiverDocumentPort;
import com.sadou.archivage.application.port.inbound.RechercherDocumentsPort;
import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.domain.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArchiverDocumentPort archiverDocumentPort;

    @MockBean
    private RechercherDocumentsPort rechercherDocumentsPort;

    @MockBean
    private DocumentMapper documentMapper;

    @MockBean
    private DocumentValidator documentValidator;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void archiverDocument_WithValidData_ShouldReturnCreatedDocument() throws Exception {
        // Given
        DocumentDto inputDto = new DocumentDto("test.pdf", "PDF", "John Doe");
        Document domainDocument = Document.creer("test.pdf", "PDF", "John Doe");
        Document savedDocument = Document.creer("test.pdf", "PDF", "John Doe");
        savedDocument.setId("123");

        when(documentMapper.toDomainEntity(any(DocumentDto.class))).thenReturn(domainDocument);
        when(archiverDocumentPort.archiverDocument(anyString(), any(User.class))).thenReturn(savedDocument);
        when(documentMapper.toDto(any(Document.class))).thenReturn(inputDto);

        // When & Then
        mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void archiverDocument_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given
        DocumentDto invalidDto = new DocumentDto(); // DTO vide sans données

        // When & Then
        mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void archiverDocument_WithMissingRequiredFields_ShouldReturnValidationErrors() throws Exception {
        // Given
        DocumentDto invalidDto = new DocumentDto();
        invalidDto.setNom(""); // Nom vide
        invalidDto.setAuteur(""); // Auteur vide

        // When & Then
        mockMvc.perform(post("/api/documents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
} 