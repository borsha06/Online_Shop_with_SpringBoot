package com.dsam.assignment01.services;

import com.dsam.assignment01.constants.SessionConstant;
import com.dsam.assignment01.forms.AddToCartForm;
import com.dsam.assignment01.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

	private final BeverageService beverageService;
	private final OrderItemService orderItemService;

	@Autowired
	public SessionServiceImpl(BeverageService beverageService, OrderItemService orderItemService) {
		this.beverageService = beverageService;
		this.orderItemService = orderItemService;
	}

	public Order addItemToOrder(HttpSession session, AddToCartForm addToCartForm) {
		Order order = getOrder(session);
		OrderItem orderItem = createOrderItem(addToCartForm, order);
		order.addOrderItem(orderItem);
		session.setAttribute(SessionConstant.ORDER, order);
		return order;
	}

	public Order getOrderFromSession(HttpSession session) {
		return getOrder(session);
	}

	public void removeOrder(HttpSession session) {
		session.removeAttribute(SessionConstant.ORDER);
	}

	private OrderItem createOrderItem(AddToCartForm addToCartForm, Order order) {
		Beverage beverage = this.beverageService.findById(addToCartForm.getBeverageId());
		OrderItem orderItem = order.findOrderItemByBeverageId(beverage.getBeverageId());
		if (orderItem != null) {
			order.removeOrderItem(orderItem);
			orderItem.incrementQuantity();
			return orderItem;
		}
		return this.orderItemService.createOrderItemFromBeverage(beverage, order.getOrderItems().size() + 1);
	}

	private Order getOrder(HttpSession session) {
		Order order = (Order) session.getAttribute(SessionConstant.ORDER);

		if (order == null) {
			order = Order.createEmpty();
		}
		return order;
	}
}
