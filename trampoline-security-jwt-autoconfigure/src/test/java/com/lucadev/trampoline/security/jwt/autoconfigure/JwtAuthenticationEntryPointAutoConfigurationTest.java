package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.JwtAuthenticationEntryPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class JwtAuthenticationEntryPointAutoConfigurationTest {
    private AnnotationConfigApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void registersJwtAuthenticationEntryPointAutomatically() {
        this.context.register(JwtAuthenticationEntryPointAutoConfiguration.class);
        this.context.refresh();
        AuthenticationEntryPoint entryPoint = context.getBean(AuthenticationEntryPoint.class);
        assertThat(entryPoint, instanceOf(JwtAuthenticationEntryPoint.class));
    }

    @Test
    public void customAuthenticationEntryPointBean() {
        this.context.register(CustomEntrypointConfig.class,
                JwtAuthenticationEntryPointAutoConfiguration.class);
        this.context.refresh();
        AuthenticationEntryPoint entryPoint = context.getBean(AuthenticationEntryPoint.class);
        assertThat(entryPoint, not(instanceOf(JwtAuthenticationEntryPoint.class)));
    }

    @Configuration
    protected static class CustomEntrypointConfig {
        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint() {
            return (httpServletRequest, httpServletResponse, e) -> {
            };
        }
    }


}