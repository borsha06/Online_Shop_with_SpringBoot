package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.RegistrationForm;
import com.dsam.assignment01.mappers.UserModelMapper;
import com.dsam.assignment01.models.*;
import com.dsam.assignment01.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AddressRepository addressRepository;
	private final Environment environment;
	private final OrderRepository orderRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, @Lazy PasswordEncoder passwordEncoder, Environment environment, OrderRepository orderRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.environment = environment;
		this.orderRepository = orderRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	public User createUser(RegistrationForm registrationForm) throws IllegalArgumentException {
		try {
			User user = UserModelMapper.fromRegistrationForm(registrationForm, passwordEncoder);
			addressRepository.save(user.getAddress());
			return userRepository.save(user);
		} catch (IllegalArgumentException exception) {
			log.error("Exception occurred");
			throw exception;
		}
	}

	public User findByUsername(String username) {
		return this.userRepository.findUserByUsername(username);
	}

	@Transactional
	public Order createOrder(Order order, String username) {
		User user = this.userRepository.findUserByUsername(username);

		Order dbOrder = this.orderRepository.save(order);
		Set<Order> orders = user.getOrders();
		orders.add(dbOrder);
		user.setOrders(orders);
		this.userRepository.save(user);

		return dbOrder;
	}

	@Transactional
	public List<Order> findOrdersByUser(String username) {
		List<Order> orders = new ArrayList<>();

		for (Order order: this.userRepository.findUserByUsername(username).getOrders()) {
			for (OrderItem orderItem: order.getOrderItems()) {
				// to make sure data loading in single session
				String name = orderItem.getOrderItemBeverage().getName();
			}
			orders.add(order);
		}

		return orders;
	}

	public void createDefaultUser() {
		if (!this.userRepository.findAll().isEmpty()) {
			return;
		}

		User user = UserModelMapper.fromEnvironment(environment, passwordEncoder);
		addressRepository.save(user.getAddress());
		userRepository.save(user);
	}

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public String findUsernameByUserId(String userId) {
		User user = this.userRepository.findById(Long.parseLong(userId)).orElse(null);
		return user != null && !user.getUsername().isBlank() ? user.getUsername() : "";
	}
}
