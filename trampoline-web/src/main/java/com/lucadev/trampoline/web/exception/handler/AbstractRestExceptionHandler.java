package com.lucadev.trampoline.web.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Abstract implementation for {@link RestExceptionHandler}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
public abstract class AbstractRestExceptionHandler<E extends Exception, R> implements RestExceptionHandler<E, R> {

	private final Class<E> exceptionClass;
	private final HttpStatus responseStatus;

	/**
	 * Construct new handler.
	 *
	 * @param exceptionClass the {@link Exception} we want to handle.
	 * @param responseStatus the http response status for the exception we want to handle.
	 */
	public AbstractRestExceptionHandler(Class<E> exceptionClass, HttpStatus responseStatus) {
		if (exceptionClass == null)
			throw new NullPointerException("Could not construct RestExceptionHandler: exceptionClass may not be null.");

		if (responseStatus == null)
			throw new NullPointerException("Could not construct RestExceptionHandler: responseStatus may not be null");

		this.exceptionClass = exceptionClass;
		this.responseStatus = responseStatus;
	}

	/**
	 * Override this to easily add more functionality.
	 *
	 * @param exception the {@link Exception} that was triggered.
	 * @param request   the request in which the exception was triggered.
	 * @return a response entity generated through the exception handled.
	 */
	@Override
	public ResponseEntity<R> handleException(E exception, HttpServletRequest request) {
		R response = handle(exception, request);
		HttpHeaders headers = headers(exception, request);
		return ResponseEntity.status(getResponseStatus()).headers(headers).body(response);
	}

	/**
	 * Configure custom response headers.
	 *
	 * @param exception the {@link Exception} that was triggered.
	 * @param request   the request that triggered the {@link Exception}
	 * @return the http headers to send back to the client.
	 */
	public HttpHeaders headers(E exception, HttpServletRequest request) {
		return new HttpHeaders();
	}

	/**
	 * Handle the exception.
	 *
	 * @param exception the {@link Exception} that was triggered.
	 * @param request   the request which triggered the exception.
	 * @return the response to the client.
	 */
	public abstract R handle(E exception, HttpServletRequest request);
}
