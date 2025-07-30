package com.sadou.archivage.infrastructure.persistence;

import com.sadou.archivage.infrastructure.persistence.entity.DocumentJpaEntity;
import com.sadou.archivage.infrastructure.persistence.specification.DocumentSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentJpaRepository extends JpaRepository<DocumentJpaEntity, String>, JpaSpecificationExecutor<DocumentJpaEntity> {
    Optional<DocumentJpaEntity> findByNom(String nom);
    
    default List<DocumentJpaEntity> findByAuteurAndDateAfter(String auteur, LocalDateTime dateLimite) {
        return findAll(DocumentSpecification.searchByAuteurAndDate(auteur, dateLimite));
    }
} 