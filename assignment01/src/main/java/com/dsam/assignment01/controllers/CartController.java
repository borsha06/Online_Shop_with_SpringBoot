package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.AddToCartForm;
import com.dsam.assignment01.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/cart")
public class CartController {

	private final SessionService sessionService;

	@Autowired
	public CartController(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@GetMapping
	public String getCart(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		model.addAttribute("order", this.sessionService.getOrderFromSession(session));
		return "cart";
	}

	@PostMapping("/add")
	public String postCart(@RequestParam("origin") String origin, @Valid AddToCartForm addToCartForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		this.sessionService.addItemToOrder(session, addToCartForm);
		return String.format("redirect:/%s", origin);
	}
}
