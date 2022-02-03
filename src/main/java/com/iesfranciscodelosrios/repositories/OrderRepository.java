package com.iesfranciscodelosrios.repositories;

import com.iesfranciscodelosrios.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
