package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.jwt.impl.TokenServiceImpl;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(TokenService.class)
@EnableConfigurationProperties(JwtSecurityProperties.class)
@AllArgsConstructor
public class TokenServiceAutoConfiguration {

    private final JwtSecurityProperties jwtSecurityProperties;
    private final TimeProvider timeProvider;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService() {
        return new TokenServiceImpl(timeProvider, userService, jwtSecurityProperties, authenticationManager);
    }

}
