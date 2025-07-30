package com.sadou.archivage.application.port.inbound;

import com.sadou.archivage.domain.entity.Document;
import com.sadou.archivage.domain.entity.User;

public interface ArchiverDocumentPort {
    Document archiverDocument(String nomFichier, User utilisateur);
} 