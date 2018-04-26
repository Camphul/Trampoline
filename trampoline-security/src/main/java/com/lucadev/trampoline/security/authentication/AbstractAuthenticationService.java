package com.lucadev.trampoline.security.authentication;

import com.lucadev.trampoline.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Abstract {@link AuthenticationService} implementation.
 * This abstraction implements a check on the {@link AuthenticationPayload} type to see if the implementation supports it.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public abstract class AbstractAuthenticationService<T extends AuthenticationPayload> implements AuthenticationService<AuthenticationPayload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuthenticationService.class);

    /**
     * Checks if the {@link AuthenticationPayload} is of the correct type.
     * If not it will not authenticate. Else it will continue authentication.
     * @param authPayload the payload containing required credentials to perform authentication.
     * @return an {@link Optional} containing our authenticated {@link User}
     */
    @Override
    public Optional<User> authenticate(AuthenticationPayload authPayload) {
        if (authPayload == null) {
            throw new IllegalArgumentException("Cannot authenticate user from null payload");
        }
        if (!isSupportedType(authPayload.getClass())) {
            LOGGER.info("Received unsupported payload type: {}", authPayload.getClass().getName());
            return attemptUnsupportedAuthenticationPayload(authPayload);
        }
        return doAuthenticate((T)authPayload);
    }

    /**
     * Method which handles any unsupported {@link AuthenticationPayload}
     * @param authPayload the {@link AuthenticationPayload} which is unsupported.
     * @return an {@link Optional} containing the authentication attempt result.
     */
    protected Optional<User> attemptUnsupportedAuthenticationPayload(AuthenticationPayload authPayload) {
        return Optional.empty();
    }

    /**
     * Does this service support the given payload type
     *
     * @param payload type of payload to check support on
     * @return if we support the payload
     */
    public abstract boolean isSupportedType(Class<? extends AuthenticationPayload> payload);

    /**
     * This method gets called after the {@link #authenticate(AuthenticationPayload)} method verified the payload type
     *
     * @param payload
     * @return
     */
    protected abstract Optional<User> doAuthenticate(T payload);
}
