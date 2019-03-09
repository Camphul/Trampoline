package com.lucadev.trampoline.web.validation;

import com.lucadev.trampoline.web.exception.handler.AbstractRestExceptionHandler;
import com.lucadev.trampoline.web.validation.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Component
public class MethodArgumentNotValidExceptionHandler extends AbstractRestExceptionHandler<MethodArgumentNotValidException, BindingResultResponse> {

	private final ValidationService validationService;

	public MethodArgumentNotValidExceptionHandler(ValidationService validationService) {
		super(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
		this.validationService = validationService;
	}

	@Override
	public BindingResultResponse handle(MethodArgumentNotValidException exception, HttpServletRequest request) {
		return validationService.createBindingResultResponse(exception.getBindingResult());
	}
}
