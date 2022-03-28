package com.dsam.assignment01.models;

import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crates")
public class Crate implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "crate_id")
	private Long crateId;

	@Min(value = 1, message = "No of bottles must be greater than zero")
	@Column(name = "no_of_bottles")
	private Integer noOfBottles;

	@Min(value = 0, message = "Crate in stock must be positive or zero")
	@Column(name = "crate_in_stock")
	private Integer cratesInStock;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bottle_id")
	private Bottle bottle;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "crate_beverage_id")
	private Beverage crateBeverage;

	@Override
	public String toString() {
		return "Crate [crateId=" + crateId + ", noOfBottles="
			+ noOfBottles + ", cratesInStock=" + cratesInStock + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(crateId)
			.append(noOfBottles)
			.append(cratesInStock)
			.hashCode();
	}
}
