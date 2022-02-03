package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

}
