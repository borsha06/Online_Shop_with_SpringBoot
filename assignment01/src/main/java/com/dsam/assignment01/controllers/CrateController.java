package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.AddCrateForm;
import com.dsam.assignment01.forms.AddToCartForm;
import com.dsam.assignment01.services.BottleService;
import com.dsam.assignment01.services.CrateService;
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
@RequestMapping(value = "/crates")
public class CrateController {
	private final CrateService crateService;
	private final BottleService bottleService;

	@Autowired
	public CrateController(CrateService crateService, BottleService bottleService) {
		this.crateService = crateService;
		this.bottleService = bottleService;
	}

	@GetMapping
	public String getCrates(Model model) {
		model.addAttribute("crates", this.crateService.findAll());
		model.addAttribute("addToCartForm", new AddToCartForm());
		return "crates";
	}

	@GetMapping("/add")
	public String getAddCrate(Model model) {
		model.addAttribute("bottles", this.bottleService.findAll());
		model.addAttribute("addCrateForm", new AddCrateForm());
		return "add_crate";
	}

	@PostMapping("/add")
	public String postAddCrate(@Valid AddCrateForm addCrateForm, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			log.info("User registration contained errors: " + addCrateForm.toString());
			return "add_crate";
		}
		if (addCrateForm.getImage() == null || addCrateForm.getImage().getSize() == 0) {
			String objectName = "addCrateForm";
			String field = "image";
			String message = "An image is required.";
			FieldError error = new FieldError(objectName, field, message);
			result.addError(error);
			return "add_crate";
		}
		crateService.createCrate(addCrateForm);
		return "redirect:/crates";
	}
}
