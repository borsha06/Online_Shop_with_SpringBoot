package com.dsam.assignment01.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleRequiredAndMinValidator implements ConstraintValidator<DoubleRequiredAndMin, Double> {
	private String name;
	private Double minValue;
	private String message;

	@Override
	public void initialize(final DoubleRequiredAndMin constraintAnnotation) {
		name = constraintAnnotation.name();
		minValue = constraintAnnotation.value();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		boolean valid = value != null && value >= minValue;

		if (!valid) {
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode(name)
				.addConstraintViolation()
				.disableDefaultConstraintViolation();
		}

		return valid;
	}
}
