package com.dsam.assignment01.models;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "address_id")
	private Long addressId;

	@NotNull(message = "Street can not be null")
	@NotEmpty(message = "Street can not be empty")
	@Column(name = "street")
	private String street;

	@NotNull(message = "House number can not be null")
	@NotEmpty(message = "House number can not be empty")
	@Column(name = "house_number")
	private String houseNumber;

	@NotNull(message = "Postal code can not be null")
	@NotEmpty(message = "Postal code can not be empty")
	@Size(min = 5, max = 5)
	@Column(name = "postal_code")
	private String postalCode;

	@NotNull(message = "City can not be null")
	@NotEmpty(message = "City can not be empty")
	@Column(name = "city")
	private String city;

	@NotNull(message = "State can not be null")
	@NotEmpty(message = "State can not be empty")
	@Column(name = "state")
	private String state;

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", street="
			+ street + ", houseNumber=" + houseNumber + ", city="
			+ city + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(addressId)
			.append(street)
			.append(houseNumber)
			.append(city)
			.append(state)
			.hashCode();
	}
}
