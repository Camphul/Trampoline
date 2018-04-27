package com.lucadev.example.trampoline.configuration;

import com.lucadev.example.trampoline.evaluator.BookAccessEvaluator;
import com.lucadev.trampoline.security.ac.AccessEvaluator;
import com.lucadev.trampoline.security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Configuration
public class AccessEvaluatorConfiguration {

    @Bean
    public AccessEvaluator bookAccessEvaluator(UserService userService) {
        return new BookAccessEvaluator(userService);
    }

}
