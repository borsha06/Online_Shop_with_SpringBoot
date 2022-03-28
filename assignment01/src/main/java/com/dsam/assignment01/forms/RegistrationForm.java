package com.dsam.assignment01.forms;

import com.google.gson.Gson;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {

	@NotNull(message = "Password can not be null")
	@NotEmpty(message = "Password can not be empty")
	@Size(min = 8, max = 30)
	private String password;

	@NotNull(message = "Retype password can not be null")
	@NotEmpty(message = "Retype password cannot be empty")
	private String retypePassword;

	@NotNull(message = "Full name can not be null")
	@NotEmpty(message = "Full name can not be empty")
	private String fullName;

	@NotNull(message = "Email can not be null")
	@NotEmpty(message = "Email can not be empty")
	@Email
	private String email;

	@NotNull(message = "Street can not be null")
	@NotEmpty(message = "Street can not be empty")
	private String street;

	@NotNull(message = "House number can not be null")
	@NotEmpty(message = "House number can not be empty")
	private String houseNumber;

	@NotNull(message = "Postal code can not be null")
	@NotEmpty(message = "Postal code can not be empty")
	@Size(min = 5, max = 5)
	private String postalCode;

	@NotNull(message = "City can not be null")
	@NotEmpty(message = "City can not be empty")
	private String city;

	@NotNull(message = "State can not be null")
	@NotEmpty(message = "State can not be empty")
	private String state;

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
