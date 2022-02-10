package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

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
				
				newOrder = orderRepository.save(newOrder);
				return newOrder;
			}else { //Insert
				order.setId(null);
				order=orderRepository.save(order);
				return order;
			}
		}else {
			order=orderRepository.save(order);
			return order;
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
