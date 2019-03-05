package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.repository.UserRepository;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.service.impl.TrampolineUserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(UserService.class)
public class UserServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UserService userService(UserRepository repository, TimeProvider timeProvider, @Value("${trampoline.security.authentication.emailIdentification:false}") boolean emailIdentification) {
        return new TrampolineUserService(repository, timeProvider, emailIdentification);
    }

}
