package com.dsam.assignment01.services;

import com.dsam.assignment01.constants.SessionConstant;
import com.dsam.assignment01.forms.AddToCartForm;
import com.dsam.assignment01.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockHttpSession;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SessionServiceTest {
	@InjectMocks
	private SessionServiceImpl sessionService;

	@Mock
	private BeverageServiceImpl mockBeverageService;

	@Mock
	private OrderItemServiceImpl mockOrderItemService;

	@Test
	public void givenFormAndOrder_whenAddItemToOrder_thenReturnOrder() {
		String name = "Mountain Dew";
		String beveragePic = "https://example.com/bottle.png";
		Double beveragePrice = 10.0;
		Double alcoholPercent = 0.0;
		Long id = 1L;
		String email = "tasfia.sharmin@gmail.com";
		String password = "password";
		String name_user = "Tasfia Sharmin";
		String street = "grune";
		String houseNumber = "60";
		String postalCode = "12345";
		String cityState = "Berlin";
		Beverage beverage = new Beverage(id, name, beveragePic, beveragePrice, alcoholPercent);
		User user = new User(id, name_user, email,password, email, "CUSTOMER", new Address(1L,street, houseNumber, postalCode,cityState,cityState), new HashSet<>());
		Order order = new Order(id, 2.0, new HashSet<>(), user);
		OrderItem expectedOrderItem = new OrderItem(id, 1, beveragePrice, 20, beverage, order);

		Mockito.when(mockBeverageService.findById(any(Long.class))).thenReturn(beverage);
		Mockito.when(mockOrderItemService.createOrderItemFromBeverage(any(Beverage.class), any(Integer.class))).thenReturn(expectedOrderItem);

		AddToCartForm addToCartForm = new AddToCartForm();
		addToCartForm.setBeverageId(id);
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(SessionConstant.ORDER, order);
		Order actualOrder = sessionService.addItemToOrder(session, addToCartForm);

		Assertions.assertNotNull(actualOrder);
		Assertions.assertEquals(expectedOrderItem.getOrder().getOrderId(), actualOrder.getOrderId());
	}

}
