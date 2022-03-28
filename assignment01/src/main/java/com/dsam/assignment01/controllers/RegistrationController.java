package com.dsam.assignment01.controllers;

import com.dsam.assignment01.forms.RegistrationForm;
import com.dsam.assignment01.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/register")
public class RegistrationController {

	private final UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String getRegistrationForm(Model model) {

		model.addAttribute("registrationForm", new RegistrationForm());
		return "register";
	}

	@PostMapping
	public String postRegistrationForm(@Valid RegistrationForm registrationForm, BindingResult result) {

		if (result.hasErrors()) {
			log.info("User registration contained errors: " + registrationForm.toString());
			return "register";
		}
		if (userService.findByUsername(registrationForm.getEmail()) != null) {
			String objectName = "registrationForm";
			String field = "email";
			String message = "An account already exists for this email.";
			FieldError error = new FieldError(objectName, field, message);
			result.addError(error);
			return "register";
		}

		userService.createUser(registrationForm);

		return "redirect:/login";
	}
}

