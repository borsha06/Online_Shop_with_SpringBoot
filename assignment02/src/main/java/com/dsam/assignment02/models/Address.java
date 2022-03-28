package com.dsam.assignment02.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
	private Long addressId;

	private String street;

	private String houseNumber;

	private String postalCode;

	private String city;

	private String state;

	public String getStandardAddress() {
		return street + " " + houseNumber + "\n" + postalCode + ", " + city + ", " + state;
	}
}
