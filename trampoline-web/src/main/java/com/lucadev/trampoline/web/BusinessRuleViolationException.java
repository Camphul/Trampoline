package com.lucadev.trampoline.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception used to notify the client that the request attempted a transaction which violates business logic.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
public class BusinessRuleViolationException extends ResponseStatusException {

	/**
	 * Construct the exception with a default message.
	 */
	public BusinessRuleViolationException() {
		this("Request violates business rule(s).");
	}

	/**
	 * Construct the exception with a reason.
	 * @param reason the reason the business rule was violated.
	 */
	public BusinessRuleViolationException(String reason) {
		super(HttpStatus.BAD_REQUEST, reason);
	}
}
