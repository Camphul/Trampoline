package com.lucadev.trampoline.web.exception.resolver;

import com.lucadev.trampoline.web.exception.ExceptionHandlerNotFoundException;
import com.lucadev.trampoline.web.exception.handler.RestExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Trampoline implementation of {@link AbstractHandlerExceptionResolver} to allow REST responses in the form of a {@code ResponseEntity}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@AllArgsConstructor
public class TrampolineRestHandlerExceptionResolver extends AbstractHandlerExceptionResolver implements RestHandlerExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineRestHandlerExceptionResolver.class);
	private final Map<Class<? extends Exception>, RestExceptionHandler> exceptionHandlerMap = new LinkedHashMap<>();

	public TrampolineRestHandlerExceptionResolver(List<RestExceptionHandler> exceptionHandlers) {
		exceptionHandlers.forEach((ex) -> addExceptionHandler(ex.getExceptionClass(), ex));
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
		responseEntity.getHeaders().toSingleValueMap().forEach(response::setHeader);
		response.setStatus(responseEntity.getStatusCodeValue());
		return generateRestResponse(responseEntity, request);
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
	 * @param exception
	 * @param handler
	 * @param <EX>
	 */
	@Override
	public <EX extends Exception> void addExceptionHandler(Class<EX> exception, RestExceptionHandler<EX, ?> handler) {
		exceptionHandlerMap.put(exception, handler);
		LOGGER.debug("Registered exception handler for {}: {}", exception.getName(), handler.getClass().getName());
	}

	/**
	 * Create a {@link ModelAndView} from the {@link ResponseEntity}
	 *
	 * @param responseEntity the REST view to return.
	 * @param request        the request in which the exception was triggered.
	 * @return the MaV to display to the client.
	 */
	protected ModelAndView generateRestResponse(ResponseEntity<?> responseEntity, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setView(resolveView(request));
		mav.addObject(responseEntity.getBody());
		return mav;
	}

	/**
	 * Resolve the view type.
	 * Falls back to JSON by default.
	 *
	 * @param request the request triggering the exception.
	 * @return the view to show the client.
	 */
	protected View resolveView(HttpServletRequest request) {
		MediaType accepted = MediaType.parseMediaType(request.getContentType());

		if (accepted.isCompatibleWith(MediaType.APPLICATION_JSON)) {
			return new MappingJackson2JsonView();
		} else if (accepted.isCompatibleWith(MediaType.APPLICATION_XML)) {
			return new MappingJackson2XmlView();
		}

		//Fallback
		return new MappingJackson2JsonView();
	}
}
