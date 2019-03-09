package com.lucadev.trampoline.validate.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

/**
 * A validation field error.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@ToString
public class FieldValidationError {

	private final String field;
	private final String error;

	public FieldValidationError(FieldError fieldError) {
		this.field = fieldError.getField();
		this.error = fieldError.getDefaultMessage();
	}
}
