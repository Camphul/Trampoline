package com.lucadev.trampoline.security;

import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * A {@link RuntimeException} which gets thrown when the current {@link User}
 * was not found. The route should already be secured when wanting to access the current user.
 * This means a server error caused the problem as it could not find the current user even though authorization succeeded.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8-12-18
 */
@Getter
@ToString
public class CurrentUserNotFoundException extends ResponseStatusException {


	/**
	 * Standard error description.
	 */
	public CurrentUserNotFoundException() {
		this("The server could not find the user behind this request.");
	}

	/**
	 * Describe the model in another way.
	 *
	 * @param message error description
	 */
	public CurrentUserNotFoundException(String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, message);
	}

}
