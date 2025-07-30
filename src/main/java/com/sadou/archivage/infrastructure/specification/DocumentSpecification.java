package com.sadou.archivage.infrastructure.specification;

import com.sadou.archivage.domain.Document;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class DocumentSpecification {

    public static Specification<Document> hasAuteur(String auteur) {
        return (root, query, criteriaBuilder) -> {
            if (auteur == null || auteur.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("auteur"), auteur);
        };
    }

    public static Specification<Document> createdAfter(LocalDateTime dateLimite) {
        return (root, query, criteriaBuilder) -> {
            if (dateLimite == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThan(root.get("dateArchivage"), dateLimite);
        };
    }

    public static Specification<Document> searchByAuteurAndDate(String auteur, LocalDateTime dateLimite) {
        return Specification.where(hasAuteur(auteur))
                          .and(createdAfter(dateLimite));
    }
} 