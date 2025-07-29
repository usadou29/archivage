package com.sadou.archivage.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String type;
    
    @Column(name = "date_archivage")
    private LocalDateTime dateArchivage = LocalDateTime.now();
    
    @Column(name = "auteur")
    private String auteur;
}
