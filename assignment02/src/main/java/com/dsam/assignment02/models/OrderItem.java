package com.dsam.assignment02.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
	private Long orderItemId;

	private Integer position;

	private Double orderItemPrice;

	private Integer quantity;

	private Beverage orderItemBeverage;
}
