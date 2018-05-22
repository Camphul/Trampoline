package com.lucadev.example.trampoline.configuration;

import com.lucadev.trampoline.security.jwt.configuration.DefaultJwtConfiguration;
import com.lucadev.trampoline.security.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Configuration
public class ExampleJwtConfiguration extends DefaultJwtConfiguration {

    @Override
    public boolean getIgnoreExpirationFlag(User user) {
        return true;
    }

    @Override
    public boolean getImpersonateFlag(User user) {
        return false;
    }

    @Override
    public void createToken(User user, Claims claims) {

    }
}
