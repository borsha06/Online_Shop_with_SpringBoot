package com.dsam.assignment01.forms;

import com.dsam.assignment01.validators.DoubleRequiredAndMin;
import com.google.gson.Gson;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCrateForm {

	@NotNull(message = "You must select a bottle")
	private Long bottleId;

	@NotNull(message = "Name can not be null")
	@NotEmpty(message = "Name cannot be empty")
	private String name;

	private MultipartFile image;

	private String beveragePic;

	@DoubleRequiredAndMin(name = "beveragePrice", value = 0.0, message = "Beverage Price must be greater than 0")
	private Double beveragePrice = 0.0;

	@DecimalMin(value = "0.0", message = "Alcohol percent must be greater than 0")
	private Double alcoholPercent = 0.0;

	@Min(value = 1, message = "No of bottles must be greater than zero")
	private Integer noOfBottles = 0;

	@Min(value = 0, message = "Crate in stock must be positive or zero")
	private Integer cratesInStock = 0;

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
