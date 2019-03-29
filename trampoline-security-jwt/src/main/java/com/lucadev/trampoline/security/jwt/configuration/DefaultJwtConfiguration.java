package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.model.User;

import java.util.Map;

/**
 * Default Jwt configuration
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
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
    public void createToken(User user, Map<String, Object> claims) {
        //Dont do anything by default
    }
}
