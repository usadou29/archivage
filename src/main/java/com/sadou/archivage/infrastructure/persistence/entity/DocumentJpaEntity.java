package com.sadou.archivage.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "documents")
@Data
public class DocumentJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String type;
    
    @Column(name = "date_archivage")
    private String dateArchivage;
    
    @Column(name = "auteur")
    private String auteur;
} 