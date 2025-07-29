package com.sadou.archivage.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Document {
    private String nom;
    private String type;
    private String auteur;
    private LocalDateTime dateArchivage;
}
