package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.RegistrationForm;
import com.dsam.assignment01.models.Order;
import com.dsam.assignment01.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
	User createUser(RegistrationForm registrationForm) throws IllegalArgumentException;

	User findByUsername(String username);

	Order createOrder(Order order, String username);

	List<Order> findOrdersByUser(String username);

	void createDefaultUser();

    List<User> findAll();

	String findUsernameByUserId(String userId);
}
