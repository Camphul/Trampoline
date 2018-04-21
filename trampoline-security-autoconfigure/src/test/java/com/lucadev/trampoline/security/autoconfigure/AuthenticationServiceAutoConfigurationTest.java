package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.authentication.AuthenticationService;
import com.lucadev.trampoline.security.authentication.UsernamePasswordAuthenticationService;
import com.lucadev.trampoline.security.exception.AuthenticationException;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */

public class AuthenticationServiceAutoConfigurationTest {
    private AnnotationConfigApplicationContext context;
    private AuthenticationManager mockedAuthenticationManager;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext();
        this.mockedAuthenticationManager = mock(AuthenticationManager.class);
        when(mockedAuthenticationManager.authenticate(any()))
                .thenThrow(new AuthenticationException("Mocking auth exception!"));
    }

    @After
    public void tearDown() throws Exception {
        if (this.context != null) {
            this.context.close();
        }

        if(this.mockedAuthenticationManager != null) {
            this.mockedAuthenticationManager = null;
        }
    }

    @Test
    public void registersUsernamePasswordAuthenticationServiceAutomatically() {
        this.context.registerBean(AuthenticationServiceAutoConfiguration.class, mockedAuthenticationManager);
        this.context.refresh();
        AuthenticationService authenticationService = this.context.getBean(AuthenticationService.class,
                mockedAuthenticationManager);
        assertThat(authenticationService, instanceOf(UsernamePasswordAuthenticationService.class));
    }

    @Test
    public void customAuthenticationServiceBean() {
        this.context.register(CustomAuthenticationServiceConfig.class);
        this.context.registerBean(AuthenticationServiceAutoConfiguration.class, mockedAuthenticationManager);
        this.context.refresh();
        AuthenticationService authenticationService = this.context.getBean(AuthenticationService.class,
                mockedAuthenticationManager);
        assertThat(authenticationService, not(instanceOf(UsernamePasswordAuthenticationService.class)));
    }

    @Configuration
    protected static class CustomAuthenticationServiceConfig {

        @Bean
        public AuthenticationService authenticationService() {
            return authPayload -> null;
        }
    }
}