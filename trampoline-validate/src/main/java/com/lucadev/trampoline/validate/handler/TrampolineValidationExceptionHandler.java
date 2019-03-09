package com.lucadev.trampoline.validate.handler;

import com.lucadev.trampoline.validate.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * A {@link ControllerAdvice} used to catch global {@link Exception} exceptions.
 * We're most interested in {@link MethodArgumentNotValidException}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@ControllerAdvice
@AllArgsConstructor
public class TrampolineValidationExceptionHandler extends ResponseEntityExceptionHandler {

	private final ValidationService validationService;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.badRequest().body(validationService.createBindingResultResponse(ex.getBindingResult()));
	}
}
