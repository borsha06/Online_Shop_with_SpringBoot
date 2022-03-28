package com.dsam.assignment01.services;

import com.dsam.assignment01.models.Beverage;
import com.dsam.assignment01.models.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	public OrderItem createOrderItemFromBeverage(Beverage beverage, int position) {
		if (beverage == null) {
			return null;
		}

		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(1);
		orderItem.setOrderItemPrice(beverage.getBeveragePrice());
		orderItem.setOrderItemBeverage(beverage);
		orderItem.setPosition(position);
		return orderItem;
	}
}
