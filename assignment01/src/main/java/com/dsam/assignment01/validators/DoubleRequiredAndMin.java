package com.dsam.assignment01.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleRequiredAndMinValidator.class)
@Documented
public @interface DoubleRequiredAndMin {
	String message() default "The field is required";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String name();

	double value();
}
