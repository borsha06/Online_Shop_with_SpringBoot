package com.dsam.assignment01.mappers;

import com.dsam.assignment01.forms.RegistrationForm;
import com.dsam.assignment01.models.Address;
import org.springframework.core.env.Environment;

public class AddressModelMapper {
	public static Address fromRegistrationForm(RegistrationForm registrationForm) {
		Address address = new Address();
		address.setStreet(registrationForm.getStreet());
		address.setHouseNumber(registrationForm.getHouseNumber());
		address.setPostalCode(registrationForm.getPostalCode());
		address.setCity(registrationForm.getCity());
		address.setState(registrationForm.getState());
		return address;
	}

	public static Address fromEnvironment(Environment environment) {
		Address address = new Address();
		address.setStreet(environment.getProperty("user.default.street"));
		address.setHouseNumber(environment.getProperty("user.default.houseNumber"));
		address.setPostalCode(environment.getProperty("user.default.postalCode"));
		address.setCity(environment.getProperty("user.default.city"));
		address.setState(environment.getProperty("user.default.state"));
		return address;
	}
}
