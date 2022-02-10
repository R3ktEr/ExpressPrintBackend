package com.iesfranciscodelosrios.repositories;

import com.iesfranciscodelosrios.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
