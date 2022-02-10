package com.iesfranciscodelosrios.repositories;

import com.iesfranciscodelosrios.model.Order;
import com.iesfranciscodelosrios.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("Select o from Order o where o.user = ?1")
	List<Order> getOrdersByUser(User u);
}
