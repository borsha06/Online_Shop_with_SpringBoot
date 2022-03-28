package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.AddToCartForm;
import com.dsam.assignment01.models.Order;

import javax.servlet.http.HttpSession;

public interface SessionService {
	Order addItemToOrder(HttpSession session, AddToCartForm orderItem);

	Order getOrderFromSession(HttpSession session);

	void removeOrder(HttpSession session);
}
