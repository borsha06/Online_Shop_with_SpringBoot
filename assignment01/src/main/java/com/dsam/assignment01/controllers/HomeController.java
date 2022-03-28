package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.AddToCartForm;
import com.dsam.assignment01.services.BottleService;
import com.dsam.assignment01.services.CrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomeController {
	private final BottleService bottleService;
	private final CrateService crateService;

	@Autowired
	public HomeController(BottleService bottleService, CrateService crateService) {
		this.bottleService = bottleService;
		this.crateService = crateService;
	}

	@GetMapping
	public String getHome(Model model) {
		model.addAttribute("bottles", this.bottleService.findAll());
		model.addAttribute("crates", this.crateService.findAll());
		model.addAttribute("addToCartForm", new AddToCartForm());
		return "home";
	}
}
