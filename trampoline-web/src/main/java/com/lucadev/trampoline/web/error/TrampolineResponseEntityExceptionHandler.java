package com.lucadev.trampoline.web.error;

import com.lucadev.trampoline.web.model.error.ApiErrorResponse;
import com.lucadev.trampoline.web.model.error.ApiFieldConstraintError;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Default trampoline rest exception handler.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/15/19
 */
@Slf4j
@ControllerAdvice
public abstract class TrampolineResponseEntityExceptionHandler
		extends ResponseEntityExceptionHandler {

	/**
	 * Parses binding result and returns errors.
	 *
	 * @param bindingResult binding result to extract errors from.
	 * @return list of field errors.
	 */
	protected List<ApiFieldConstraintError> parseBindingResult(
			BindingResult bindingResult) {
		List<ApiFieldConstraintError> errors = new ArrayList<>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			errors.add(new ApiFieldConstraintError(error.getField(),
					error.getDefaultMessage()));
		}
		for (ObjectError error : bindingResult.getGlobalErrors()) {
			errors.add(new ApiFieldConstraintError(error.getObjectName(),
					error.getDefaultMessage()));
		}
		return errors;
	}

	/**
	 * Handles method argument validation exceptions.
	 *
	 * @param ex      the validation exception.
	 * @param headers http header requests.
	 * @param status  http status.
	 * @param request web request.
	 * @return response entity with error.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		List<ApiFieldConstraintError> errors = parseBindingResult(ex.getBindingResult());
		ApiErrorResponse<ApiFieldConstraintError> apiError = new ApiErrorResponse(
				HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(),
				request);
	}

	/**
	 * Handles binding exceptions.
	 *
	 * @param ex      the validation exception.
	 * @param headers http header requests.
	 * @param status  http status.
	 * @param request web request.
	 * @return response entity with error.
	 */
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex,
														 HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		List<ApiFieldConstraintError> errors = parseBindingResult(ex.getBindingResult());
		ApiErrorResponse<ApiFieldConstraintError> apiError = new ApiErrorResponse(
				HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(),
				request);
	}

	/**
	 * Handles type mismatch validation exceptions.
	 *
	 * @param ex      the validation exception.
	 * @param headers http header requests.
	 * @param status  http status.
	 * @param request web request.
	 * @return response entity with error.
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
														HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		String error = ex.getValue() + " value for " + ex.getPropertyName()
				+ " should be of type " + ex.getRequiredType();

		ApiErrorResponse<String> apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles missing request part exceptions.
	 *
	 * @param ex      the validation exception.
	 * @param headers http header requests.
	 * @param status  http status.
	 * @param request web request.
	 * @return response entity with error.
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(
			MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		String error = ex.getRequestPartName() + " part is missing";
		ApiErrorResponse<String> apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles missing servlet request param exceptions.
	 *
	 * @param ex      the validation exception.
	 * @param headers http header requests.
	 * @param status  http status.
	 * @param request web request.
	 * @return response entity with error.
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		String error = ex.getParameterName() + " parameter is missing";
		ApiErrorResponse<String> apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles method argument type mismatch exceptions.
	 *
	 * @param ex      mismatch exception.
	 * @param request web request.
	 * @return error response.
	 */
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		String error = ex.getName() + " should be of type "
				+ ex.getRequiredType().getName();

		ApiErrorResponse<String> apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST,
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles constraint validation exceptions.
	 *
	 * @param ex      constraint exception.
	 * @param request web request.
	 * @return error response.
	 */
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex, WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		List<ApiFieldConstraintError> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(new ApiFieldConstraintError(
					((PathImpl) violation.getPropertyPath()).getLeafNode().getName(),
					violation.getMessage()));
		}

		ApiErrorResponse<ApiFieldConstraintError> apiError = new ApiErrorResponse(
				HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles 404 not found exceptions.
	 *
	 * @param ex      404 exceptions.
	 * @param headers http headers.
	 * @param status  http status.
	 * @param request web request.
	 * @return error response.
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		String error = "No handler found for " + ex.getHttpMethod() + " "
				+ ex.getRequestURL();

		ApiErrorResponse<String> apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND,
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles http 405 method not supported.
	 *
	 * @param ex      http exception.
	 * @param headers http headers.
	 * @param status  http status.
	 * @param request web request.
	 * @return error response.
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		//
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(
				" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		ApiErrorResponse<String> apiError = new ApiErrorResponse(
				HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(),
				builder.toString());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles 415 media type not supported.
	 *
	 * @param ex      http exception.
	 * @param headers http headers.
	 * @param status  http status.
	 * @param request web request.
	 * @return error response.
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		ApiErrorResponse<String> apiError = new ApiErrorResponse(
				HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
				builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

	/**
	 * Handles internal server error 500.
	 *
	 * @param ex      exception causing a 500.
	 * @param request web request.
	 * @return error response.
	 */
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		log.debug("Exception advice for {}", ex.getClass().getName());
		logger.error("error", ex);
		ApiErrorResponse<String> apiError = new ApiErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(),
				apiError.getStatus());
	}

}
