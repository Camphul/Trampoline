package com.lucadev.trampoline.web.validation;

import org.springframework.validation.BindingResult;

/**
 * Interface which defines a bunch of methods required for trampoline-validation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
public interface ValidationService {

	/**
	 * Create a {@link BindingResultResponse}
	 *
	 * @param bindingResult the validation errors.
	 * @return the {@link BindingResultResponse}
	 */
	BindingResultResponse createBindingResultResponse(BindingResult bindingResult);

}
