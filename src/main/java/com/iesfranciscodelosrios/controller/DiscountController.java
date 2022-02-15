package com.iesfranciscodelosrios.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
	@Autowired
	DiscountService service;

	private static final Logger logger = LogManager.getLogger();
	@ApiOperation(value = "getAllDiscounts", notes = "returns all discounts")
	@ApiResponse(code = 200, message = "OK.  resource is fetched successfully",response=List.class)
	@GetMapping
	public ResponseEntity<List<Discount>> getAllDiscounts() {
		List<Discount> discounts = service.getAllDiscounts();
		return new ResponseEntity<List<Discount>>(discounts, new HttpHeaders(), HttpStatus.OK);
	}
	
	@ApiModelProperty(notes = "Discount id",name="id_d",required=true,value="1")
	@ApiOperation(value = "getDiscountById", notes = "return a discount by id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK. resource is fetched successfully",response=Discount.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 404, message = "NOT_FOUND. The discount has not been found"),
	})
	@GetMapping("/{id_d}")
	public ResponseEntity<Discount> getDiscountById(@PathVariable("id_d") Long id_d) {
		HttpStatus httpstatus;

		Discount discount = new Discount();
		Long id_discount;

		try {
			discount = service.getDiscountById(id_d);
			id_discount = discount.getId();

			if (id_discount.equals(id_d)) {
				httpstatus = HttpStatus.OK;
			} else {
				logger.info("El descuento no existe");
				httpstatus = HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpstatus = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<Discount>(discount, new HttpHeaders(), httpstatus);
	}
	@ApiOperation(value = "createOrUpdatediscount", notes = "return a discount by id")
	@ApiResponse(code=200, message="OK. Discount created or updated sucefully")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<Discount> createOrUpdatediscount(@RequestBody Discount d) {
		Discount discount = service.createOrUpdatediscount(d);
		return new ResponseEntity<Discount>(discount, new HttpHeaders(), HttpStatus.OK);
	}
	
	@ApiModelProperty(notes = "Delete a discount by id",name="id_d",required=true,value="1")
	@ApiOperation(value = "deleteDiscountById", notes = "return a discount by id")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "OK. resource is deleted successfully",response=Discount.class),
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 404, message = "NOT_FOUND. The discount has not been found"),
	})
	@DeleteMapping("/{id_d}")
	public HttpStatus deleteDiscountById(@PathVariable("id_d") Long id_d) {
		try {
			Discount discount = service.getDiscountById(id_d);
			if (discount.getId().equals(id_d)) {
				service.deleteDiscountById(id_d);
				return HttpStatus.OK;
			} else {
				logger.info("El descuento no se ha podido borrar");
				return HttpStatus.BAD_REQUEST;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("El descuento no ha sido encontrado");
			return HttpStatus.NOT_FOUND;
		}
	}

}
