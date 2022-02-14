package com.iesfranciscodelosrios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iesfranciscodelosrios.model.Document;
import com.iesfranciscodelosrios.services.DocumentService;

@RestController 
@RequestMapping("/documents") 
public class DocumentController {
	@Autowired
	DocumentService service;
	
	@GetMapping
	public ResponseEntity<List<Document>> getAllDocuments(){
		List<Document> documents=service.getAllDocuments();
		return new ResponseEntity<List<Document>>(documents, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id_o}/{id_d}")
	public ResponseEntity<Document> getDocumentById(@PathVariable("id_o") Long id_o, @PathVariable("id_d") Long id_d){
		HttpStatus httpstatus;
		
		Document document=new Document();
		Long id_order;
		
		try {
			document = service.getDocumentById(id_d);
			id_order = document.getOrder().getId();
			
			if(id_order.equals(id_o)) {
				httpstatus=HttpStatus.OK;
			}else {
				httpstatus=HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Document>(document, new HttpHeaders(), httpstatus);
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Document> createOrUpdateDocument(@RequestBody Document d){
		HttpStatus httpstatus;
		
		Document document;
		try {
			document = service.createOrUpdateDocument(d);
			httpstatus=HttpStatus.OK;
		} catch (Exception e) {
			document=new Document();
			httpstatus=HttpStatus.NOT_FOUND;
			e.printStackTrace();
		}
		return new ResponseEntity<Document>(document, new HttpHeaders(), httpstatus);
	}
	
	@DeleteMapping("/{id_u}/{id_d}")
	public HttpStatus deleteDocumentById(@PathVariable("id_u")Long id_u, @PathVariable("id_d")Long id_d) {
		try {
			System.out.println(id_d);
			Document document = service.getDocumentById(id_d);
			Long id_userdb = document.getOrder().getUser().getId();
			
			if(id_userdb.equals(id_u)) {
				service.deleteDocumentById(id_d);				
				return HttpStatus.OK;
			}else {
				return HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.NOT_FOUND;				
		}
	}
}
