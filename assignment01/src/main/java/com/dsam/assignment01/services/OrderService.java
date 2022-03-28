package com.dsam.assignment01.services;

import com.dsam.assignment01.models.Order;

public interface OrderService {
	Order findById(Long orderId);
}
