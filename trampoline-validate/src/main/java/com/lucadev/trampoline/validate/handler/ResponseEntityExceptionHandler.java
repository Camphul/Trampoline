package com.lucadev.trampoline.validate.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

/**
 * Interface which defines methods required to handle global model.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public interface ResponseEntityExceptionHandler<T extends Exception, R> {

	/**
	 * Handle a {@link Exception}
	 *
	 * @param exception  the {@link Exception} to handle.
	 * @param webRequest the current request.
	 * @return a {@link ResponseEntity} to show the client.
	 */
	ResponseEntity<R> handle(T exception, WebRequest webRequest);
}
