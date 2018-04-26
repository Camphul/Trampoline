package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.jwt.impl.TokenServiceImpl;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TokenServiceAutoConfigurationTest {
    private AnnotationConfigApplicationContext context;
    private JwtSecurityProperties jwtSecurityProperties;
    private TimeProvider timeProvider;
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        jwtSecurityProperties = mock(JwtSecurityProperties.class);
        timeProvider = mock(TimeProvider.class);
        userService = mock(UserService.class);
        context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        if (this.context != null) {
            this.context.close();
        }
        if (jwtSecurityProperties != null) {
            jwtSecurityProperties = null;
        }
        if (timeProvider != null) {
            timeProvider = null;
        }
        if (userService != null) {
            userService = null;
        }
    }

    @Test
    public void registersTokenServiceImplAutomatically() {
        this.context.registerBean(TokenServiceAutoConfiguration.class, jwtSecurityProperties,
                timeProvider, userService);
        this.context.refresh();
        TokenService tokenService = this.context.getBean(TokenService.class);
        assertThat(tokenService, instanceOf(TokenServiceImpl.class));
    }

    @Test
    public void customTokenServiceBean() {
        this.context.register(CustomTokenServiceConfig.class);
        this.context.registerBean(TokenServiceAutoConfiguration.class, jwtSecurityProperties,
                timeProvider, userService);
        this.context.refresh();
        TokenService tokenService = this.context.getBean(TokenService.class);
        assertThat(tokenService, not(instanceOf(TokenServiceImpl.class)));
    }

    @Configuration
    protected static class CustomTokenServiceConfig {

        @Bean
        public TokenService tokenService() {
            return mock(TokenService.class);
        }
    }
}