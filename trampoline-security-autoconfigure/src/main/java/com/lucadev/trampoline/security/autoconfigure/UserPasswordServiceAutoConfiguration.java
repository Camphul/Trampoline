package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.service.UserPasswordService;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.service.impl.TrampolineUserPasswordService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(UserPasswordService.class)
public class UserPasswordServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UserPasswordService userService(UserService userService, PasswordEncoder passwordEncoder) {
        return new TrampolineUserPasswordService(userService, passwordEncoder);
    }

}
