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
import com.iesfranciscodelosrios.services.OrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController 
@RequestMapping("/orders") 
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@ApiOperation(value="Get all orders",notes="Returns all the orders with id")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=List.class)	
	})
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders(){
		List<Order> orders=orderService.getAllOrder();
		return new ResponseEntity<List<Order>>(orders, new HttpHeaders(), HttpStatus.OK);
	}
	
	@ApiOperation(value="Get order by id",notes="Returns the order corresponding to the order_id and user_id passed")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=Order.class),
		@ApiResponse(code=404, message="Order not found"),
		@ApiResponse(code=400, message="Bad request")
	})
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
	
	@ApiOperation(value="Get orders by user",notes="Returns all the orders correspondig to a user")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=List.class),
		@ApiResponse(code=404, message="User or orders not found")
	})
	@GetMapping("/{id_u}")
	public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable("id_u") Long id_u){
		HttpStatus httpstatus;
		List<Order> orders;
		
		try {
			orders = orderService.getOrdersByUser(id_u); 
			
			httpstatus=HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			
			orders=new ArrayList<Order>();
			httpstatus=HttpStatus.NOT_FOUND;
		}
		
		return new ResponseEntity<List<Order>>(orders, new HttpHeaders(), httpstatus);
	}
	
	@ApiOperation(value="Create and update orders",notes="Returns the created or update order")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=List.class),
		@ApiResponse(code=404, message="Order not found")
	})
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<Order> createOrUpdateOrder(@RequestBody Order o){
		HttpStatus httpstatus;
		Order order;
		try {
			order = orderService.createOrUpdateOrder(o);
			httpstatus=HttpStatus.OK;
		} catch (Exception e) {
			order=new Order();
			httpstatus=HttpStatus.NOT_FOUND;
			e.printStackTrace();
		}
		return new ResponseEntity<Order>(order, new HttpHeaders(), httpstatus);
	}
	
	@ApiOperation(value="Delete order",notes="Delete a specified order only if the specified user has it")
	@ApiResponses(value= {	
		@ApiResponse(code=200, message="Successfull operation",response=List.class),
		@ApiResponse(code=404, message="Order not found"),
		@ApiResponse(code=400, message="Bad request")
	})
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
	//todo get pedidos sin cobrar/sin entregar
}
