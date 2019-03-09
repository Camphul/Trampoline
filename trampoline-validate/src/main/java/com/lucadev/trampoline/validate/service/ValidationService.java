package com.lucadev.trampoline.validate.service;

import com.lucadev.trampoline.validate.model.ValidationErrorResponse;
import org.springframework.validation.BindingResult;

/**
 * Interface which defines a bunch of methods required for trampoline-validation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public interface ValidationService {

	/**
	 * Create a {@link ValidationErrorResponse}
	 *
	 * @param bindingResult the validation errors.
	 * @return the {@link ValidationErrorResponse}
	 */
	ValidationErrorResponse createValidationErrorResponse(BindingResult bindingResult);

}
