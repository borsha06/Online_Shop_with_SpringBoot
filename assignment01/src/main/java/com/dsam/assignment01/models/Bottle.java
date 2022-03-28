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
@Table(name = "bottles")
public class Bottle implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bottle_id")
	private Long bottleId;

	@Positive(message = "Volume must be positive")
	@Column(name = "volume")
	private Double volume;

	@NotNull(message = "Supplier can not be null")
	@NotEmpty(message = "Supplier can not be empty")
	@Column(name = "supplier")
	private String supplier;

	@Min(value = 0, message = "Bottle in stock must be positive or zero")
	@Column(name = "bottle_in_stock")
	private Integer bottleInStock;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "beverage_id")
	private Beverage bottleBeverage;

	@Override
	public String toString() {
		return "Bottle [bottleId=" + bottleId + ", volume="
			+ volume + ", supplier=" + supplier + ", bottleInStock="
			+ bottleInStock + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(bottleId)
			.append(volume)
			.append(supplier)
			.append(bottleInStock)
			.hashCode();
	}
}
