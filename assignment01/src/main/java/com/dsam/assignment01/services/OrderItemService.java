package com.dsam.assignment01.services;

import com.dsam.assignment01.models.Beverage;
import com.dsam.assignment01.models.OrderItem;

public interface OrderItemService {
	OrderItem createOrderItemFromBeverage(Beverage beverage, int position);
}
