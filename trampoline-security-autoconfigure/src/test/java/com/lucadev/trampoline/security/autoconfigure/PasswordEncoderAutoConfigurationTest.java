package com.lucadev.trampoline.security.autoconfigure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class PasswordEncoderAutoConfigurationTest {
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
    public void registersBCryptAutomatically() {
        this.context.register(PasswordEncoderAutoConfiguration.class);
        this.context.refresh();
        PasswordEncoder encoder = this.context.getBean(PasswordEncoder.class);
        assertThat(encoder, instanceOf(BCryptPasswordEncoder.class));
    }

    @Test
    public void customPasswordEncoderBean() {
        this.context.register(CustomPassEncoderBeanConfig.class, PasswordEncoderAutoConfiguration.class);
        this.context.refresh();
        PasswordEncoder encoder = this.context.getBean(PasswordEncoder.class);
        assertThat(encoder, not(instanceOf(BCryptPasswordEncoder.class)));
    }

    @Configuration
    protected static class CustomPassEncoderBeanConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new PasswordEncoder() {
                @Override
                public String encode(CharSequence charSequence) {
                    return null;
                }

                @Override
                public boolean matches(CharSequence charSequence, String s) {
                    return false;
                }
            };
        }
    }
}