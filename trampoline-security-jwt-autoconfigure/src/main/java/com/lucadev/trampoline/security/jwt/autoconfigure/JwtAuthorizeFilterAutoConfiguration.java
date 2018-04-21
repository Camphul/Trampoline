package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.configuration.JwtProperties;
import com.lucadev.trampoline.security.jwt.JwtService;
import com.lucadev.trampoline.security.jwt.JwtTrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(TrampolineAuthorizeFilter.class)
@EnableConfigurationProperties(JwtProperties.class)
@AllArgsConstructor
@Import(JwtServiceAutoConfiguration.class)
public class JwtAuthorizeFilterAutoConfiguration {

    private final JwtProperties jwtProperties;

    @Bean
    @ConditionalOnMissingBean
    public TrampolineAuthorizeFilter trampolineAuthorizeFilter(JwtService jwtService, UserService userService) {
        return new JwtTrampolineAuthorizeFilter(userService, jwtService, jwtProperties);
    }

}
