package com.iesfranciscodelosrios.repositories;

import com.iesfranciscodelosrios.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("Select o from Order o where o.user.id = ?1")
	List<Order> getOrdersByUser(Long id_user);
}
