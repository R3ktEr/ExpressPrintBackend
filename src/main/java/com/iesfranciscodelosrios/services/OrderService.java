package com.iesfranciscodelosrios.services;

import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.model.User;
import com.iesfranciscodelosrios.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public List<Order> getAllOrder() {
		List<Order> orders=orderRepository.findAll();
		return orders;
	}
	
	public Order getOrderById(Long id) throws Exception {
		Optional<Order> order=orderRepository.findById(id);
		if(order.isPresent()) {
			return order.get();
		}else {
			logger.info("El pedido con id "+id+"no existe");
			throw new Exception("El pedido con id "+id+"no existe");
		}
	}
	
	public List<Order> getOrdersByUser(Long id_user) throws Exception {
		List<Order> orders=orderRepository.getOrdersByUser(id_user);
		if(!orders.isEmpty()) {
			return orders;
		}else {
			logger.info("El usuario no tiene pedidos");
			throw new Exception("El usuario no tiene pedidos");
		}
	}
	
	public Order createOrUpdateOrder(Order order) throws Exception {
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
				logger.info("El pedido con id "+order.getId()+"no existe");
				throw new Exception("El pedido con id "+order.getId()+"no existe");
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
					logger.info("El usuario del pedido no es el mismo que el de la base de datos");
					throw new Exception("El usuario del pedido no es el mismo que el de la base de datos");
				}
				
				return order;
			} catch (Exception e) {
				logger.info("El usuario asociado al pedido no existe");
				throw new Exception("El usuario asociado al pedido no existe");
			}
		}
		
	}
	
	public void deleteOrderById(Long id) throws Exception{
		Optional<Order> order=orderRepository.findById(id);
		if(order.isPresent()) {
			orderRepository.deleteById(id);
		}else {
			logger.info("El pedidio no existe");
			throw new Exception("El pedido no existe");
		}
	}
}
