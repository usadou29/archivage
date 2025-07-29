package com.sadou.archivage.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;      // Utilisé comme identifiant de connexion

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;   // Stocké hashé !

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles")
    @Column(name = "role")
    private Set<String> roles; // ex : ADMIN, EDITOR, VIEWER

    private LocalDateTime createdAt = LocalDateTime.now();
}
