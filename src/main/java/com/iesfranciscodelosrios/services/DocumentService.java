package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.Document;
import com.iesfranciscodelosrios.repositories.DocumentRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    public List<Document> getAllDocuments() {
		List<Document> documents=documentRepository.findAll();
		return documents;
	}
	
	public Document getDocumentById(Long id) throws Exception {
		Optional<Document> document=documentRepository.findById(id);
		if(document.isPresent()) {
			return document.get();
		}else {
			throw new Exception("El documento no existe");
		}
	}
	
	public Document createOrUpdateDocument(Document document) {
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
				document.setId(null);
				document=documentRepository.save(document);
				return document;
			}
		}else {
			document=documentRepository.save(document);
			return document;
		}
	}
	
	public void deleteDocumentById(Long id) throws Exception{
		Optional<Document> document=documentRepository.findById(id);
		if(document.isPresent()) {
			documentRepository.deleteById(id);
		}else {
			throw new Exception("El documento no existe");
		}
	}
}
