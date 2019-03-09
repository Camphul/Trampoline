package com.lucadev.trampoline.web.exception.resolver;

import com.lucadev.trampoline.web.exception.handler.RestExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface which extends {@link HandlerExceptionResolver} to handle REST-like responses for exceptions.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
public interface RestHandlerExceptionResolver extends HandlerExceptionResolver {

	/**
	 * Resolves {@link RestExceptionHandler} and invokes it.
	 *
	 * @param ex      the exception to resolve and handle.
	 * @param request the request which triggered the exception
	 * @return the response to the client.
	 */
	ResponseEntity handleException(Exception ex, HttpServletRequest request);

	/**
	 * Add a {@link RestExceptionHandler}
	 *
	 * @param exception the {@link Exception} to hook into.
	 * @param handler the handler to register.
	 * @param <EX> the type of the exception.
	 */
	<EX extends Exception> void addExceptionHandler(Class<EX> exception, RestExceptionHandler<EX, ?> handler);

	/**
	 * Find the {@link RestExceptionHandler} linked to the {@link Exception}
	 *
	 * @param ex the exeption to find a handler of.
	 * @return the handler for that exception.
	 */
	RestExceptionHandler resolveExceptionHandler(Exception ex);
}
