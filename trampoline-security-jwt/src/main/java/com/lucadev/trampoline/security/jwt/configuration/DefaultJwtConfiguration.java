package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.model.User;
import io.jsonwebtoken.Claims;

/**
 * Default Jwt configuration
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 22-5-18
 */
public class DefaultJwtConfiguration implements JwtConfiguration {

    @Override
    public boolean getIgnoreExpirationFlag(User user) {
        return false;
    }

    @Override
    public boolean getImpersonateFlag(User user) {
        return false;
    }

    @Override
    public void createToken(User user, Claims claims) {
        //Dont do anything by default
    }
}
