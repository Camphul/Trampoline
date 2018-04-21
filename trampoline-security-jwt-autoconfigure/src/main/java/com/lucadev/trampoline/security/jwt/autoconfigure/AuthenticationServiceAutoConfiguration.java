package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.authentication.AuthenticationService;
import com.lucadev.trampoline.security.jwt.authentication.UsernamePasswordAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Autoconfig for setting the default service to authenticate from credential payloads.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(AuthenticationService.class)
@AllArgsConstructor
public class AuthenticationServiceAutoConfiguration {

    private final AuthenticationManager authenticationManager;

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationService authenticationService() {
        return new UsernamePasswordAuthenticationService(authenticationManager);
    }

}
