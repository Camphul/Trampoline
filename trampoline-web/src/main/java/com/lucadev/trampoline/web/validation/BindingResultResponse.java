package com.lucadev.trampoline.web.validation;

import com.lucadev.trampoline.model.MessageResponse;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * A {@link MessageResponse} extension for validation errors found through a {@link }
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@ToString
public class BindingResultResponse extends MessageResponse {

	private final Map<String, String> fieldErrors;

	/**
	 * Construct a new validation error response.
	 *
	 * @param message       the message to add to the response.
	 * @param bindingResult the {@link BindingResult} to create the model around.
	 */
	public BindingResultResponse(String message, BindingResult bindingResult) {
		super(message);
		this.fieldErrors = bindingResult.getFieldErrors()
				.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
	}
}
