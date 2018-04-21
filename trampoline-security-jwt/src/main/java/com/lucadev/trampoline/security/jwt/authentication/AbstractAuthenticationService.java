package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Abstract {@link AuthenticationService} which checks if the passed authentication payload type is supported by this service.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public abstract class AbstractAuthenticationService<T extends AuthenticationPayload> implements AuthenticationService<AuthenticationPayload>{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuthenticationService.class);

    @Override
    public Optional<User> authenticate(AuthenticationPayload authPayload) {
        if(authPayload == null) {
            throw new IllegalArgumentException("Cannot authenticate user from null payload");
        }
        if(!isSupportedType(authPayload)) {
            LOGGER.warn("Received unsupported payload type: {}", authPayload.getClass().getName());
            return attemptUnsupportedAuthenticationPayload(authPayload);
        }
        return doAuthenticate((T)authPayload);
    }

    /**
     * We have the option to attempt to authenticate unsupoorted payload types.
     * @param authPayload
     * @return
     */
    protected Optional<User> attemptUnsupportedAuthenticationPayload(AuthenticationPayload authPayload) {
        return Optional.empty();
    }

    /**
     * Does this service support the given payload type
     * @param payload type of payload to check support on
     * @return if we support the payload
     */
    public abstract boolean isSupportedType(AuthenticationPayload payload);

    /**
     * This method gets called after the {@link #authenticate(AuthenticationPayload)} method verified the payload type
     * @param payload
     * @return
     */
    protected abstract Optional<User> doAuthenticate(T payload);
}
