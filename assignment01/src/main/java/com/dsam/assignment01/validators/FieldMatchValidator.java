package com.dsam.assignment01.validators;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;
	private String message;

	@Override
	public void initialize(final FieldMatch constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			final Object firstField = BeanUtils.getProperty(value, firstFieldName);
			final Object secondField = BeanUtils.getProperty(value, secondFieldName);

			valid = (firstField == null && secondField == null) || (firstField != null && firstField.equals(secondField));
		} catch (final Exception ignore) {

		}

		if (!valid) {
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode(firstFieldName)
				.addConstraintViolation()
				.disableDefaultConstraintViolation();
		}

		return valid;
	}
}
