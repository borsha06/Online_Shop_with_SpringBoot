package com.dsam.assignment01.services;

import com.dsam.assignment01.models.*;
import com.dsam.assignment01.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public Order findById(Long orderId) {
		Order order = this.orderRepository.findOrderByOrderId(orderId);
		for (OrderItem orderItem: order.getOrderItems()) {
			// to make sure data loading in single session
			String name = orderItem.getOrderItemBeverage().getName();
		}
		return order;
	}
}
