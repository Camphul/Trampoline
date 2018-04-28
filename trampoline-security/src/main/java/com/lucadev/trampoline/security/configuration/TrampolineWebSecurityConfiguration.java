package com.lucadev.trampoline.security.configuration;

import com.lucadev.trampoline.security.authentication.TrampolineAuthenticationManager;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * Spring {@link WebSecurityConfigurerAdapter} to configure our own services/routes.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
@Order(1)
public class TrampolineWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Wrap the {@link AuthenticationManager} into our own {@link TrampolineAuthenticationManager}
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationManagerBean();
    }

    /**
     * @see #authenticationManager()
     * {@inheritDoc}
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new TrampolineAuthenticationManager(new ArrayList<>(), super.authenticationManagerBean());
    }
}
