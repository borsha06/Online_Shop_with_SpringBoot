package com.dsam.assignment01.controllers;

import com.dsam.assignment01.constants.RoleConstant;
import com.dsam.assignment01.models.Order;
import com.dsam.assignment01.models.User;
import com.dsam.assignment01.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/orders")
public class OrderController {

	private final SessionService sessionService;
	private final UserService userService;
	private final CloudPubsubService cloudPubsubService;
	private final OrderService orderService;

	@Autowired
	public OrderController(SessionService sessionService, UserService userService, CloudPubsubService cloudPubsubService, OrderService orderService) {
		this.sessionService = sessionService;
		this.userService = userService;
		this.cloudPubsubService = cloudPubsubService;
		this.orderService = orderService;
	}

	@GetMapping
	public String getOrders(Model model, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		List<Order> orders = this.userService.findOrdersByUser(username);

		model.addAttribute("orders", orders);
		return "orders";
	}

	@PostMapping
	public String postOrders(HttpServletRequest request) throws IOException, InterruptedException {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();
		Order order = this.sessionService.getOrderFromSession(session);
		Order dbOrder = this.userService.createOrder(order, username);
		this.sessionService.removeOrder(session);
		this.cloudPubsubService.publishMessage(dbOrder);
		return "redirect:/orders";
	}

	@GetMapping("/{orderId}")
	public String getOrderDetails(@PathVariable(value = "orderId") Long orderId, Model model, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		User user = this.userService.findByUsername(username);
		Order order = this.orderService.findById(orderId);

		if (Objects.equals(user.getRole(), RoleConstant.ADMIN)) {
			model.addAttribute("order", order);
			return "order_details";
		}

		List<Order> orders = this.userService.findOrdersByUser(username);
		boolean ownOrder = orders.stream().anyMatch(item -> Objects.equals(item.getOrderId(), orderId));
		if (ownOrder) {
			model.addAttribute("order", order);
			return "order_details";
		}

		return "redirect:/";
	}
}
