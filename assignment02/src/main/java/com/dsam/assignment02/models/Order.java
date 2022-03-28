package com.dsam.assignment02.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
	private Long orderId;

	private Double orderPrice;

	private List<OrderItem> orderItems;

	private User user;

	public Order() {
		orderItems = new ArrayList<>();
	}
}
