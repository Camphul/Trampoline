package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.JwtProperties;
import com.lucadev.trampoline.security.jwt.JwtService;
import com.lucadev.trampoline.security.jwt.impl.JwtServiceImpl;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(JwtService.class)
@EnableConfigurationProperties(JwtProperties.class)
@AllArgsConstructor
public class JwtServiceAutoConfiguration {

    private final JwtProperties jwtProperties;

    @Bean
    @ConditionalOnMissingBean
    public JwtService jwtService(TimeProvider timeProvider, UserService userService) {
        return new JwtServiceImpl(timeProvider, userService, jwtProperties);
    }

}
