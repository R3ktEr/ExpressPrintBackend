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
import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.services.DocumentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController 
@RequestMapping("/documents") 
public class DocumentController {
	@Autowired
	DocumentService service;
	
	@ApiOperation(value="Get all documents",notes="Returns all the documents with id")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=List.class)	
	})
	@GetMapping
	public ResponseEntity<List<Document>> getAllDocuments(){
		List<Document> documents=service.getAllDocuments();
		return new ResponseEntity<List<Document>>(documents, new HttpHeaders(), HttpStatus.OK);
	}
	
	@ApiOperation(value="Get order by id",notes="Returns the order corresponding to the order_id and document_id passed")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=Order.class),
		@ApiResponse(code=404, message="Order not found"),
		@ApiResponse(code=400, message="Bad request")
	})
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
	
	@ApiOperation(value="Create and update document",notes="Returns the created or update document")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=List.class),
		@ApiResponse(code=404, message="Order not found")
	})
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
	
	@ApiOperation(value="Delete document",notes="Delete a specified document only if the specified user has it")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=List.class),
		@ApiResponse(code=404, message="Order not found"),
		@ApiResponse(code=400, message="Bad request")
	})
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
