package com.lucadev.trampoline.web.rest.error.validation;

import com.lucadev.trampoline.web.rest.error.exception.handler.AbstractRestExceptionHandler;
import com.lucadev.trampoline.web.rest.error.exception.handler.RestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

/**
 * A {@link RestExceptionHandler} for {@link MethodArgumentNotValidException}
 * <p>
 * Which gets triggered when validation of a RequestBody fails.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Component
public class MethodArgumentNotValidExceptionHandler extends AbstractRestExceptionHandler<MethodArgumentNotValidException, BindingResultResponse> {

	private final ValidationService validationService;

	/**
	 * Construct new handler.
	 *
	 * @param validationService handler used to make responses.
	 */
	public MethodArgumentNotValidExceptionHandler(ValidationService validationService) {
		super(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
		this.validationService = validationService;
	}

	/**
	 * Construct our response.
	 *
	 * @param exception the {@link Exception} that was triggered.
	 * @param request   the request which triggered the exception.
	 * @return response built from the exception which was handled.
	 */
	@Override
	public BindingResultResponse handle(MethodArgumentNotValidException exception, HttpServletRequest request) {
		return validationService.createBindingResultResponse(exception.getBindingResult());
	}
}
