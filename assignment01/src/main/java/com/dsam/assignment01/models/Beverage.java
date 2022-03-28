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
@Table(name = "beverages")
public class Beverage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "beverage_id")
	private Long beverageId;

	@NotNull(message = "Name can not be null")
	@NotEmpty(message = "Name can not be empty")
	@Column(name = "name")
	private String name;

	/*@Pattern(regexp = "^((https?|ftp|file)://|/)[a-zA-Z0-9+@#/%?=_.\\-]*[a-zA-Z0-9+&@#/%=_\\-]", message = "Beverage pic must be valid url")*/
	@Column(name = "beverage_pic")
	private String beveragePic;

	@Positive(message = "Price must be positive")
	@Column(name = "beverage_price")
	private Double beveragePrice;

	@Column(name = "alcohol_percent")
	private Double alcoholPercent;

	public boolean isAlcoholic() {
		return alcoholPercent > 0.0;
	}

	@Override
	public String toString() {
		return "Beverage [beverageId=" + beverageId + ", name="
			+ name + ", beveragePic=" + beveragePic + ", beveragePrice="
			+ beveragePrice + ", alcoholPercent=" + alcoholPercent + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(beverageId)
			.append(name)
			.append(beveragePic)
			.append(beveragePrice)
			.append(alcoholPercent)
			.hashCode();
	}
}
