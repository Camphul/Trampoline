package com.lucadev.trampoline.web.rest.error.exception.resolver;

import com.lucadev.trampoline.web.rest.error.exception.ExceptionHandlerNotFoundException;
import com.lucadev.trampoline.web.rest.error.exception.handler.RestExceptionHandler;
import com.lucadev.trampoline.web.rest.error.exception.internal.ResponseEntityResponseProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Trampoline implementation of {@link AbstractHandlerExceptionResolver} to allow REST responses in the form of a {@code ResponseEntity}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@AllArgsConstructor
public class TrampolineRestHandlerExceptionResolver extends AbstractHandlerExceptionResolver implements RestHandlerExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineRestHandlerExceptionResolver.class);
	private final Map<Class<? extends Exception>, RestExceptionHandler> exceptionHandlerMap = new LinkedHashMap<>();
	private final ResponseEntityResponseProcessor responseProcessor;


	public TrampolineRestHandlerExceptionResolver(List<RestExceptionHandler> exceptionHandlers, ResponseEntityResponseProcessor responseProcessor) {
		exceptionHandlers.forEach((ex) -> addExceptionHandler(ex.getExceptionClass(), ex));
		this.responseProcessor = responseProcessor;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		ResponseEntity<?> responseEntity;

		try {
			responseEntity = handleException(e, request);
		} catch (ExceptionHandlerNotFoundException ex) {
			LOGGER.warn("No exception handler has been found for {}", e.getClass());
			return null;
		}

		processResponse(responseEntity, request, response);

		return new ModelAndView();
	}

	private void processResponse(ResponseEntity<?> responseEntity, HttpServletRequest request, HttpServletResponse response) {
		try {
			MethodParameter methodParameter = responseProcessor.getMethodParameter(RestExceptionHandler.class,
					"handleException", new Class[]{Exception.class, HttpServletRequest.class});
			responseProcessor.processResponse(methodParameter, responseEntity, request, response);
		} catch (Exception e) {
			LOGGER.error("Failed to process exception handle.", e);
		}
	}

	/**
	 * Resolves {@link RestExceptionHandler} and invokes it.
	 *
	 * @param ex      the exception to resolve and handle.
	 * @param request the request which triggered the exception
	 * @return the response to the client.
	 */
	@Override
	public ResponseEntity handleException(Exception ex, HttpServletRequest request) {
		RestExceptionHandler exceptionHandler = resolveExceptionHandler(ex);
		return exceptionHandler.handleException(ex, request);
	}

	/**
	 * Find the {@link RestExceptionHandler} linked to the {@link Exception}
	 *
	 * @param ex the exeption to find a handler of.
	 * @return the handler for that exception.
	 */
	@Override
	public RestExceptionHandler resolveExceptionHandler(Exception ex) {
		if (ex == null)
			throw new ExceptionHandlerNotFoundException("Could not resolve exception handler: the exception is null.");

		//Also accept super classes etc...
		for (Class clazz = ex.getClass(); clazz != Throwable.class; clazz = clazz.getSuperclass()) {
			if (exceptionHandlerMap.containsKey(clazz)) {
				return exceptionHandlerMap.get(clazz);
			}
		}

		throw new ExceptionHandlerNotFoundException("Could not resolve exception to handler.");
	}

	/**
	 * Register a new {@link RestExceptionHandler}
	 *
	 * @param exception the {@link Exception} to hook into.
	 * @param handler   the new handler to register.
	 * @param <EX>      the type of the exception.
	 */
	@Override
	public <EX extends Exception> void addExceptionHandler(Class<EX> exception, RestExceptionHandler<EX, ?> handler) {
		exceptionHandlerMap.put(exception, handler);
		LOGGER.debug("Registered exception handler for {}: {}", exception.getName(), handler.getClass().getName());
	}

}
