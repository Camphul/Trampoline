package com.lucadev.trampoline.validate.service;

import com.lucadev.trampoline.validate.model.FieldValidationError;
import com.lucadev.trampoline.validate.model.ValidationErrorResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Default {@link ValidationService} implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public class TrampolineValidationService implements ValidationService {

	@Override
	public ValidationErrorResponse createValidationErrorResponse(BindingResult bindingResult) {
		List<FieldValidationError> errors =
				bindingResult.getAllErrors().stream().map(
						err -> new FieldValidationError((FieldError) err)).collect(Collectors.toList());

		return new ValidationErrorResponse("Failed to validate object.", errors);
	}
}
