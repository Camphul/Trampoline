package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.autoconfigure.PasswordEncoderAutoConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
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
        if(this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void registersBCryptAutomatically() {
        this.context.register(PasswordEncoderAutoConfiguration.class);
        this.context.refresh();
        PasswordEncoder encoder = this.context.getBean(PasswordEncoder.class);
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    public void customPasswordEncoderBean() {
        this.context.register(CustomPassEncoderBeanConfig.class, PasswordEncoderAutoConfiguration.class);
        this.context.refresh();
        PasswordEncoder encoder = this.context.getBean(PasswordEncoder.class);
        assertFalse(encoder instanceof BCryptPasswordEncoder);
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