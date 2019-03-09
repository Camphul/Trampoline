package com.lucadev.trampoline.web.validation;

import org.springframework.validation.BindingResult;

/**
 * Default {@link ValidationService} implementation.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public class TrampolineValidationService implements ValidationService {

	@Override
	public BindingResultResponse createBindingResultResponse(BindingResult bindingResult) {
		return new BindingResultResponse("Validation error has occurred.", bindingResult);
	}
}
