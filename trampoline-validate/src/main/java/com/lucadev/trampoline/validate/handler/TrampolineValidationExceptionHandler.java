package com.lucadev.trampoline.validate.handler;

import com.lucadev.trampoline.validate.model.ValidationErrorResponse;
import com.lucadev.trampoline.validate.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * A {@link ControllerAdvice} used to catch global {@link Exception} exceptions.
 * We're most interested in {@link MethodArgumentNotValidException}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@ControllerAdvice
@AllArgsConstructor
public class TrampolineValidationExceptionHandler implements ResponseEntityExceptionHandler<MethodArgumentNotValidException,
		ValidationErrorResponse> {

	private final ValidationService validationService;

	@Override
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<ValidationErrorResponse> handle(MethodArgumentNotValidException exception, WebRequest webRequest) {
		return ResponseEntity.badRequest().body(validationService.createValidationErrorResponse(exception.getBindingResult()));
	}
}
