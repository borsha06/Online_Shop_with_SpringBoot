package com.dsam.assignment01.mappers;

import com.dsam.assignment01.constants.RoleConstant;
import com.dsam.assignment01.forms.RegistrationForm;
import com.dsam.assignment01.models.User;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

public class UserModelMapper {
	public static User fromRegistrationForm(RegistrationForm registrationForm, PasswordEncoder passwordEncoder) {
		return fromRegistrationForm(registrationForm, passwordEncoder, RoleConstant.CUSTOMER);
	}

	public static User fromRegistrationForm(RegistrationForm registrationForm, PasswordEncoder passwordEncoder, String role) {
		User user = new User();
		user.setFullName(registrationForm.getFullName());
		user.setUsername(registrationForm.getEmail());
		user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
		user.setEmail(registrationForm.getEmail());
		user.setRole(role);
		user.setAddress(AddressModelMapper.fromRegistrationForm(registrationForm));
		user.setOrders(new HashSet<>());
		return user;
	}

	public static User fromEnvironment(Environment environment, PasswordEncoder passwordEncoder) {
		User user = new User();
		user.setFullName(environment.getProperty("user.default.fullName"));
		user.setUsername(environment.getProperty("user.default.email"));
		user.setPassword(passwordEncoder.encode(environment.getProperty("user.default.password")));
		user.setEmail(environment.getProperty("user.default.email"));
		user.setRole(RoleConstant.ADMIN);
		user.setAddress(AddressModelMapper.fromEnvironment(environment));
		user.setOrders(new HashSet<>());
		return user;
	}
}
