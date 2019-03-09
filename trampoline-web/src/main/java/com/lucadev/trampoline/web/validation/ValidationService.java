package com.lucadev.trampoline.web.validation;

import com.lucadev.trampoline.web.validation.BindingResultResponse;
import org.springframework.validation.BindingResult;

/**
 * Interface which defines a bunch of methods required for trampoline-validation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
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
