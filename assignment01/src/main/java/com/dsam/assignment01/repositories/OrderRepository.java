package com.dsam.assignment01.repositories;

import com.dsam.assignment01.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Order findOrderByOrderId(Long orderId);
}
