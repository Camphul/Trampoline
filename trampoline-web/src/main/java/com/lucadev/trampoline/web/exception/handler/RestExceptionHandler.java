package com.lucadev.trampoline.web.exception.handler;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * REST exception handle.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public interface RestExceptionHandler<E extends Exception, R> {

	/**
	 * Handle the {@link Exception}
	 *
	 * @param exception the {@link Exception} that was triggered.
	 * @param request   the request in which the exception was triggered.
	 * @return the response.
	 */
	ResponseEntity<R> handleException(E exception, HttpServletRequest request);

	/**
	 * Type of {@link Exception} accepted in this handler.
	 *
	 * @return the class of the accepted exception.
	 */
	Class<E> getExceptionClass();
}
