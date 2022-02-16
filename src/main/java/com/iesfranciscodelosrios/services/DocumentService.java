package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.Document;
import com.iesfranciscodelosrios.repositories.DocumentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    
    private static final Logger logger = LogManager.getLogger(OrderService.class);
	
	public Document getDocumentById(Long id) throws Exception {
		Optional<Document> document=documentRepository.findById(id);
		if(document.isPresent()) {
			return document.get();
		}else {
			logger.info("El documento con id "+id+"no existe");
			throw new Exception("El documento con id "+id+" no existe");
		}			
	}
	
	public List<Document> saveDocuments(List<Document> documents) throws Exception {
		List<Document> result=new ArrayList<Document>();
		
		if(!documents.isEmpty()) {
			for (Document document : documents) {
				try{
					Document d=documentRepository.save(document);					
					result.add(d);
				}catch(Exception e) {
					e.printStackTrace();
					logger.info("Uno de los precios asociados al documento no existe en la base de datos");
					throw new Exception("Uno de los precios asociados al documento no existe en la base de datos");
				}
			}			
		}else {
			logger.info("La lista de documentos que se intenta guardar esta vacia");
			throw new Exception("La lista de documentos que se intenta guardar esta vacia");
		}
		
		return result;
	}
}
