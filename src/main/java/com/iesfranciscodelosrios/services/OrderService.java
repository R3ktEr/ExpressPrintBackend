package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DiscountService discountService;
    @Autowired
    UserService userService;

    public List<Order> getAllOrder() {
		List<Order> orders=orderRepository.findAll();
		return orders;
	}
	
	public Order getOrderById(Long id) throws Exception {
		Optional<Order> order=orderRepository.findById(id);
		if(order.isPresent()) {
			return order.get();
		}else {
			throw new Exception("El pedido no existe");
		}
	}
	
	public List<Order> getOrdersByUser(Long id_user) throws Exception {
		List<Order> orders=orderRepository.getOrdersByUser(id_user);
		if(!orders.isEmpty()) {
			return orders;
		}else {
			throw new Exception("El usuario no tiene pedidos");
		}
	}
	
	public Order createOrUpdateOrder(Order order) {
		if(order.getId()!=null && order.getId()>0) {
			Optional<Order> o=orderRepository.findById(order.getId());
			if(o.isPresent()) { //Update
				Order newOrder = o.get();
				newOrder.setId(order.getId());
				newOrder.setPickupDate(order.getPickupDate());
				newOrder.setUser(order.getUser());
				newOrder.setPayed(order.isPayed());
				newOrder.setPickedUp(order.isPickedUp());
				newOrder.setFinalPrice(order.getFinalPrice());
				
				newOrder.setDiscounts(discountService.getAllDiscounts());
				
				newOrder = orderRepository.save(newOrder);
				return newOrder;
			}else {
				//lanzar excepcion de not found
				return new Order();
			}
		}else { //Insert
			order.setId(null);
			order.setOrderDate(LocalDateTime.now());
			order.setDiscounts(discountService.getAllDiscounts());
			
			/**
			 * Compruebo que el usuario pasado por JSON exista en la base de datos y me lo traigo ya persistido
			 */
			User u1=order.getUser();
			
			try {
				User u2=userService.findUserByMail(u1.getMail());
				if(u1.equals(u2)&&(u1.getMail().equals(u2.getMail()))) {
					order.setUser(u2);
					order=orderRepository.save(order);			
				}else {
					order=new Order();
				}
				
				return order;
			} catch (Exception e) {
				e.printStackTrace();
				return new Order();
			}
		}
		
	}
	
	public void deleteOrderById(Long id) throws Exception{
		Optional<Order> order=orderRepository.findById(id);
		if(order.isPresent()) {
			orderRepository.deleteById(id);
		}else {
			throw new Exception("El pedido no existe");
		}
	}
}
