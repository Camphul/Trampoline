package com.lucadev.trampoline.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which gets thrown when no exception handler could be found.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed to handle exception.")
public class ExceptionHandlerNotFoundException extends RuntimeException {

	public ExceptionHandlerNotFoundException(String message) {
		super(message);
	}
}
