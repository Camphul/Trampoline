package com.lucadev.trampoline.security.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A {@link RuntimeException} which gets thrown when the current {@link com.lucadev.trampoline.security.model.User}
 * was not found. The route should already be secured when wanting to access the current user.
 * This means a server error caused the problem as it could not find the current user even though authorization succeeded.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 8-12-18
 */
@Getter
@ToString
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CurrentUserNotFoundException extends RuntimeException {

    private final String message;

    /**
     * Describe the exception in another way.
     *
     * @param message error description
     */
    public CurrentUserNotFoundException(String message) {
        this.message = message;
    }

    /**
     * Standard error description.
     */
    public CurrentUserNotFoundException() {
        this("The server could not find the user behind this request.");
    }

}
