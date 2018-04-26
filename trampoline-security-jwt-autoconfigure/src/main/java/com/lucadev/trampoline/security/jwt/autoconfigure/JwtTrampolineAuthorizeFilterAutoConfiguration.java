package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.jwt.JwtTrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.service.UserService;
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
@ConditionalOnClass(TrampolineAuthorizeFilter.class)
@EnableConfigurationProperties(JwtSecurityProperties.class)
@AllArgsConstructor
public class JwtTrampolineAuthorizeFilterAutoConfiguration {

    private final JwtSecurityProperties jwtSecurityProperties;
    private final TokenService tokenService;
    private final UserService userService;

    @Bean
    @ConditionalOnMissingBean
    public TrampolineAuthorizeFilter trampolineAuthorizeFilter() {
        return new JwtTrampolineAuthorizeFilter(userService, tokenService, jwtSecurityProperties);
    }

}
