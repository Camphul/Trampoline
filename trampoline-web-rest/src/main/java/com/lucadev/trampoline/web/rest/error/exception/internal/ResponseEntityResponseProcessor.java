package com.lucadev.trampoline.web.rest.error.exception.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.FixedContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Some Spring interfaces still work with ModelAndView while we want to return a ResponseEntity. This is a solution.
 * Mostly used internally.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Component
public class ResponseEntityResponseProcessor implements InitializingBean {

	public static final MediaType DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON;
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseEntityResponseProcessor.class);
	private List<HttpMessageConverter<?>> messageConverters;

	private ContentNegotiationManager contentNegotiationManager;
	private HandlerMethodReturnValueHandler responseProcessor;
	private HandlerMethodReturnValueHandler fallbackResponseProcessor;

	public ResponseEntityResponseProcessor(List<HttpMessageConverter<?>> messageConverters) {
		this.messageConverters = messageConverters;
	}

	/**
	 * Once the bean has set its properties.
	 */
	@Override
	public void afterPropertiesSet() {
		if (contentNegotiationManager == null) {
			contentNegotiationManager = new ContentNegotiationManager(
					new HeaderContentNegotiationStrategy(), new FixedContentNegotiationStrategy(DEFAULT_MEDIA_TYPE));
		}
		responseProcessor = new HttpEntityMethodProcessor(messageConverters, contentNegotiationManager);
		fallbackResponseProcessor = new HttpEntityMethodProcessor(messageConverters,
				new ContentNegotiationManager(new FixedContentNegotiationStrategy(DEFAULT_MEDIA_TYPE)));
	}

	/**
	 * Obtain wrapper around Java's {@link Method} for a method that returns a {@link ResponseEntity}
	 *
	 * @param clazz      class
	 * @param methodName method in the class
	 * @param params     the method parameters.
	 * @return the {@link MethodParameter}
	 */
	public MethodParameter getMethodParameter(Class<?> clazz, String methodName, Class[] params) {
		Method method = ClassUtils.getMethod(clazz, methodName, params);

		MethodParameter returnTypeMethodParam = new MethodParameter(method, -1);
		// This method caches the resolved value, so it's convenient to initialize it
		// only once here.
		returnTypeMethodParam.getGenericParameterType();

		return returnTypeMethodParam;
	}

	/**
	 * Configure the response to use the value from the returnTypeMethodParam
	 *
	 * @param returnTypeMethodParam the method which returns a ResponseEntity.
	 * @param entity                the entity data.
	 * @param request               the actual request
	 * @param response              the response to write to.
	 * @throws Exception when we cannot write the exception
	 */
	public void processResponse(MethodParameter returnTypeMethodParam, ResponseEntity<?> entity,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		NativeWebRequest webRequest = new ServletWebRequest(request, response);

		MethodParameter methodParameter = new MethodParameter(returnTypeMethodParam);
		ModelAndViewContainer mavContainer = new ModelAndViewContainer();

		try {
			responseProcessor.handleReturnValue(entity, methodParameter, mavContainer, webRequest);
		} catch (HttpMediaTypeNotAcceptableException ex) {
			LOGGER.debug("Requested media type is not supported, falling back to default one");
			fallbackResponseProcessor.handleReturnValue(entity, methodParameter, mavContainer, webRequest);
		}
	}

}
