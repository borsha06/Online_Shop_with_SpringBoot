package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.AddBottleForm;
import com.dsam.assignment01.forms.AddToCartForm;
import com.dsam.assignment01.services.BottleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping(value = "/bottles")
public class BottleController {
	private final BottleService bottleService;

	@Autowired
	public BottleController(BottleService bottleService) {
		this.bottleService = bottleService;
	}

	@GetMapping
	public String getBottles(Model model) {
		model.addAttribute("bottles", this.bottleService.findAll());
		model.addAttribute("addToCartForm", new AddToCartForm());
		return "bottles";
	}

	@GetMapping("/add")
	public String getAddBottle(Model model) {
		model.addAttribute("addBottleForm", new AddBottleForm());
		return "add_bottle";
	}

	@PostMapping("/add")
	public String postAddBottle(@Valid AddBottleForm addBottleForm, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			log.info("Adding bottle has errors: " + addBottleForm.toString());
			return "add_bottle";
		}
		if (addBottleForm.getImage() == null || addBottleForm.getImage().getSize() == 0) {
			String objectName = "addBottleForm";
			String field = "image";
			String message = "An image is required.";
			FieldError error = new FieldError(objectName, field, message);
			result.addError(error);
			return "add_bottle";
		}
		this.bottleService.createBottle(addBottleForm);
		return "redirect:/bottles";
	}
}
