package com.lucadev.trampoline.web.model.error;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Represents a single field error.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/15/19
 */
@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class ApiFieldConstraintError {

	/**
	 * Field name.
	 */
	private final String field;

	/**
	 * Error message.
	 */
	private final String message;

}
