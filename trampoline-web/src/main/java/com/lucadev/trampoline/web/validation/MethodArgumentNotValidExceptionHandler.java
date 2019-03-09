package com.lucadev.trampoline.web.validation;

import com.lucadev.trampoline.web.exception.handler.AbstractRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@link com.lucadev.trampoline.web.exception.handler.RestExceptionHandler} for {@link MethodArgumentNotValidException}
 *
 * Which gets triggered when validation of a RequestBody fails.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Component
public class MethodArgumentNotValidExceptionHandler extends AbstractRestExceptionHandler<MethodArgumentNotValidException, BindingResultResponse> {

	private final ValidationService validationService;

	/**
	 * Construct new handler.
	 * @param validationService handler used to make responses.
	 */
	public MethodArgumentNotValidExceptionHandler(ValidationService validationService) {
		super(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
		this.validationService = validationService;
	}

	/**
	 * Construct our response.
	 * @param exception the {@link Exception} that was triggered.
	 * @param request   the request which triggered the exception.
	 * @return
	 */
	@Override
	public BindingResultResponse handle(MethodArgumentNotValidException exception, HttpServletRequest request) {
		return validationService.createBindingResultResponse(exception.getBindingResult());
	}
}
