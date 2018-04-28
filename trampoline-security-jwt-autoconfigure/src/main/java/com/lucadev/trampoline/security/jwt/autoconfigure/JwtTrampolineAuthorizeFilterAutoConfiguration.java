package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.jwt.impl.JwtTrampolineAuthorizeFilter;
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
@AllArgsConstructor
public class JwtTrampolineAuthorizeFilterAutoConfiguration {

    private final TokenService tokenService;

    @Bean
    @ConditionalOnMissingBean
    public TrampolineAuthorizeFilter trampolineAuthorizeFilter() {
        return new JwtTrampolineAuthorizeFilter(tokenService);
    }

}
