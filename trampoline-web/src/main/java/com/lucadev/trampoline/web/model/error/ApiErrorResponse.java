package com.lucadev.trampoline.web.model.error;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collection;

/**
 * Basic API error response.
 *
 * @param <T> type of errors such as string or custom error.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/15/19
 */
@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class ApiErrorResponse<T> {

	private final HttpStatus status;

	private final String message;

	private final Collection<T> errors;

	public ApiErrorResponse(HttpStatus status, String message, T error) {
		this(status, message, Arrays.asList(error));
	}

}
