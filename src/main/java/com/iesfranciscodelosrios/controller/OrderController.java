package com.iesfranciscodelosrios.controller;

import java.util.ArrayList;
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

import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.services.OrderService;
import com.iesfranciscodelosrios.services.UserService;

@RestController 
@RequestMapping("/orders") 
public class OrderController {
	@Autowired
	OrderService orderService;
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<Order>> getAllDocuments(){
		List<Order> orders=orderService.getAllOrder();
		return new ResponseEntity<List<Order>>(orders, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id_u}/{id_o}")
	public ResponseEntity<Order> getOrderById(@PathVariable("id_u") Long id_u, @PathVariable("id_o") Long id_o){
		HttpStatus httpstatus;
		
		Order order=new Order();
		Long id_user;
		
		try {
			order = orderService.getOrderById(id_o);
			id_user = order.getUser().getId();
			
			if(id_user.equals(id_u)) {
				httpstatus=HttpStatus.OK;
			}else {
				httpstatus=HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<Order>(order, new HttpHeaders(), httpstatus);
	}
	
	@GetMapping("/{id_u}")
	public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable("id_u") Long id_u){
		HttpStatus httpstatus;
		List<Order> orders;
		
		try {
			User u=userService.findUserById(id_u); 
			orders = orderService.getOrdersByUser(u); 
			
			httpstatus=HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			
			orders=new ArrayList<Order>();
			httpstatus=HttpStatus.NOT_FOUND;
		}
		
		return new ResponseEntity<List<Order>>(orders, new HttpHeaders(), httpstatus);
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Order> createOrUpdateOrder(@RequestBody Order o){
		Order order=orderService.createOrUpdateOrder(o);
		return new ResponseEntity<Order>(order, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id_u}/{id_o}")
	public HttpStatus deleteOrderById(@PathVariable("id_u")Long id_u, @PathVariable("id_o")Long id_o) {
		try {	
			Order order = orderService.getOrderById(id_o);
			Long id_user = order.getUser().getId();
			
			if(id_user.equals(id_u)) {
				orderService.deleteOrderById(id_o);				
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
