package com.dsam.assignment02.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	private Long userId;

	private String fullName;

	private String email;

	private Address address;
}
