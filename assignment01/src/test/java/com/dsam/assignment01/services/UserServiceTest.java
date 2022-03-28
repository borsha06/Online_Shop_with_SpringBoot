package com.dsam.assignment01.services;

import com.dsam.assignment01.forms.RegistrationForm;
import com.dsam.assignment01.models.*;
import com.dsam.assignment01.models.Order;
import com.dsam.assignment01.repositories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository mockUserRepository;

	@Mock
	private AddressRepository mockAddressRepository;

	@Mock
	private OrderRepository mockOrderRepository;

	@Mock
	private BCryptPasswordEncoder mockPasswordEncoder;

	@Mock
	private Environment mockEnvironment;

	@Test
	public void givenUsername_whenFindByUsername_thenReturnUser() {
		long userId = 1;
		String email = "tasfia.sharmin@gmail.com";
		User expectedUser= new User(userId, email, "123456", "Tasfia Sharmin", email, "CUSTOMER", new Address(), new HashSet<>());
		Mockito.when(mockUserRepository.findUserByUsername(email)).thenReturn(expectedUser);

		User actualUser = userService.findByUsername(email);
		Assertions.assertNotNull(actualUser);
		Assertions.assertEquals(expectedUser.getUserId(), actualUser.getUserId());
		Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
		Assertions.assertEquals(expectedUser.getFullName(), actualUser.getFullName());
		Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		Assertions.assertEquals(expectedUser.getRole(), actualUser.getRole());
		Assertions.assertIterableEquals(expectedUser.getOrders(), actualUser.getOrders());
	}

	@Test
	public void givenRegistrationForm_whenCreateUser_thenReturnUser() {
		String email = "tasfia.sharmin@gmail.com";
		String password = "password";
		String name = "Tasfia Sharmin";
		String street = "grune";
		String houseNumber = "60";
		String postalCode = "12345";
		String cityState = "Berlin";
		RegistrationForm registrationForm = new RegistrationForm(password, password, name, email, street, houseNumber, postalCode, cityState, cityState);
		Address address = new Address(null, street, houseNumber, postalCode, cityState, cityState);
		User expectedUser= new User(null, email, password, name, email, "CUSTOMER", address, new HashSet<>());
		Mockito.when(mockPasswordEncoder.encode(any(String.class))).thenReturn(password);
		Mockito.when(mockAddressRepository.save(any(Address.class))).thenReturn(address);
		Mockito.when(mockUserRepository.save(any(User.class))).thenReturn(expectedUser);

		User actualUser = userService.createUser(registrationForm);
		Assertions.assertNotNull(actualUser);
		Assertions.assertEquals(expectedUser.getUserId(), actualUser.getUserId());
		Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
		Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
		Assertions.assertEquals(expectedUser.getFullName(), actualUser.getFullName());
		Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		Assertions.assertEquals(expectedUser.getRole(), actualUser.getRole());
		Assertions.assertIterableEquals(expectedUser.getOrders(), actualUser.getOrders());

		Assertions.assertEquals(expectedUser.getAddress().getAddressId(), actualUser.getAddress().getAddressId());
		Assertions.assertEquals(expectedUser.getAddress().getStreet(), actualUser.getAddress().getStreet());
		Assertions.assertEquals(expectedUser.getAddress().getHouseNumber(), actualUser.getAddress().getHouseNumber());
		Assertions.assertEquals(expectedUser.getAddress().getPostalCode(), actualUser.getAddress().getPostalCode());
		Assertions.assertEquals(expectedUser.getAddress().getCity(), actualUser.getAddress().getCity());
		Assertions.assertEquals(expectedUser.getAddress().getState(), actualUser.getAddress().getState());
	}

	@Test
	public void givenOrderUsername_whenCreateOrder_thenReturnOrder() {
		String email = "tasfia.sharmin@gmail.com";
		String password = "password";
		String name = "Tasfia Sharmin";
		String street = "grune";
		String houseNumber = "60";
		String postalCode = "12345";
		String cityState = "Berlin";
		Order order = new Order();
		order.setOrderId(1L);
		Address address = new Address(null, street, houseNumber, postalCode, cityState, cityState);
		HashSet<Order> orders = new HashSet<>();
		orders.add(order);
		User expectedUser= new User(null, email, password, name, email, "CUSTOMER", address, orders);
		Mockito.when(mockUserRepository.save(any(User.class))).thenReturn(expectedUser);
		Mockito.when(mockUserRepository.findUserByUsername(email)).thenReturn(expectedUser);
		Mockito.when(mockOrderRepository.save(any(Order.class))).thenReturn(order);

		Order actualOrder = userService.createOrder(order, email);
		Assertions.assertNotNull(actualOrder);
		Assertions.assertEquals(order.getOrderId(), actualOrder.getOrderId());
	}

	@Test
	public void givenUsername_whenFindOrdersByUser_thenReturnUserOrders() {
		String email = "tasfia.sharmin@gmail.com";
		String password = "password";
		String name = "Tasfia Sharmin";
		Order order = new Order();
		order.setOrderItems(new HashSet<>());
		HashSet<Order> orders = new HashSet<>();
		orders.add(order);
		User expectedUser= new User(null, email, password, name, email, "CUSTOMER", null, orders);
		Mockito.when(mockUserRepository.findUserByUsername(email)).thenReturn(expectedUser);

		List<Order> actualOrders = userService.findOrdersByUser(email);
		Assertions.assertNotNull(actualOrders);
		Assertions.assertEquals(1, actualOrders.size());
		Assertions.assertIterableEquals(expectedUser.getOrders(), actualOrders);
	}

	@Test
	public void givenUserId_whenFindUsernameByUserId_thenReturnUsername() {
		Long userId = 1L;
		String email = "tasfia.sharmin@gmail.com";
		User expectedUser= new User(userId, email, "123456", "Tasfia Sharmin", email, "CUSTOMER", new Address(), new HashSet<>());
		Mockito.when(mockUserRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

		String actualUsername = userService.findUsernameByUserId(userId.toString());
		Assertions.assertNotNull(actualUsername);
		Assertions.assertEquals(expectedUser.getUsername(), actualUsername);
	}
}
