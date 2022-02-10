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

import com.iesfranciscodelosrios.model.Discount;
import com.iesfranciscodelosrios.services.DiscountService;

@RestController 
@RequestMapping("/discounts")
public class DiscountController {
	@Autowired
	DiscountService service;
	
	@GetMapping
	public ResponseEntity<List<Discount>> getAllDiscounts(){
		List<Discount> discounts=service.getAllDiscounts();
		return new ResponseEntity<List<Discount>>(discounts, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id_d}")
	public ResponseEntity<Discount> getDiscountById(@PathVariable("id_d") Long id_d){
		HttpStatus httpstatus;
		
		Discount discount=new Discount();
		Long id_discount;
		
		try {
			discount = service.getDiscountById(id_d);
			id_discount = discount.getId();
			
			if(id_discount.equals(id_d)) {
				httpstatus=HttpStatus.OK;
			}else {
				httpstatus=HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Discount>(discount, new HttpHeaders(), httpstatus);
	}
	
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Discount> createOrUpdatediscount(@RequestBody Discount d){
		Discount discount=service.createOrUpdatediscount(d);
		return new ResponseEntity<Discount>(discount, new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id_d}")
	public HttpStatus deleteDiscountById(@PathVariable("id_d")Long id_d) {
		try {
			Discount discount = service.getDiscountById(id_d);
			if(discount.getId().equals(id_d)) {
				service.deleteDiscountById(id_d);			
				return HttpStatus.OK;
			}else {
				return HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return HttpStatus.NOT_FOUND;
		}
	}
	
	
}
