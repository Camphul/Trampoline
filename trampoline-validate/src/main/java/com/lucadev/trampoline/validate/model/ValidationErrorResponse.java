package com.lucadev.trampoline.validate.model;

import com.lucadev.trampoline.model.MessageResponse;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * A {@link MessageResponse} extension for validation errors.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@ToString
public class ValidationErrorResponse extends MessageResponse {

	private final List<FieldValidationError> fieldErrors;

	/**
	 * Construct a new validation error response.
	 *
	 * @param message
	 * @param fieldErrors
	 */
	public ValidationErrorResponse(String message, List<FieldValidationError> fieldErrors) {
		super(message);
		this.fieldErrors = fieldErrors;
	}
}
