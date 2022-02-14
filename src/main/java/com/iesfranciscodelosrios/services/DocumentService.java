package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.Document;
import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.repositories.DocumentRepository;

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
    @Autowired
    OrderService orderService;
    
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public List<Document> getAllDocuments() {
		List<Document> documents=documentRepository.findAll();
		return documents;
	}
	
	public Document getDocumentById(Long id) throws Exception {
		Optional<Document> document=documentRepository.findById(id);
		if(document.isPresent()) {
			return document.get();
		}else {
			logger.info("El documento con id "+id+"no existe");
			throw new Exception("El documento no existe");
		}			
	}
	
	public Document createOrUpdateDocument(Document document) throws Exception {
		if(document.getId()!=null && document.getId()>0) {
			Optional<Document> d=documentRepository.findById(document.getId());
			if(d.isPresent()) { //Update
				Document newDocument = d.get();
				newDocument.setId(document.getId());
				newDocument.setCopyPrice(document.getCopyPrice());
				newDocument.setnCopies(document.getnCopies());
				newDocument.setIsColor(document.getIsColor());
				newDocument.setSize(document.getSize());
				newDocument.setThickness(document.getThickness());
				newDocument.setTwoSides(document.isTwoSides());
				newDocument.setFinishType(document.getFinishType());
				newDocument.setImpressionPerSide(document.getImpressionPerSide());
				newDocument.setVertical(document.isVertical());
				newDocument.setRingedPosition(document.getRingedPosition());
				newDocument.setComment(document.getComment());
				newDocument.setUrl(document.getUrl());
				
				newDocument = documentRepository.save(newDocument);
				return newDocument;
			}else { //Insert
				logger.info("El documento con id "+document.getId()+"no existe");
				throw new Exception("El documento con id "+document.getId()+"no existe");
			}
		}else {
			Order o1=document.getOrder();
			
			try {
				Order o2=orderService.getOrderById(o1.getId());
				
				if(o2.equals(o1)) {
					document.setOrder(o2);
					document=documentRepository.save(document);
				}else {
					logger.info("El pedido del documento no es el mismo que el de la base de datos");
					throw new Exception("El pedido del documento no es el mismo que el de la base de datos");
				}
				
				return document;
			} catch (Exception e) {
				logger.info("El pedido asociado al documento no existe");
				throw new Exception("El pedido asociado al documento no existe");
			}
			
		}
	}
	
	public void deleteDocumentById(Long id) throws Exception{
		Optional<Document> document=documentRepository.findById(id);
		if(document.isPresent()) {
			documentRepository.deleteById(id);
		}else {
			logger.info("El documento no existe");
			throw new Exception("El documento no existe");
		}
	}
}
