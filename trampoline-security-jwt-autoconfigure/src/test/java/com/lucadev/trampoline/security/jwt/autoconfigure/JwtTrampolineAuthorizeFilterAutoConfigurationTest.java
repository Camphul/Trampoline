package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.jwt.impl.JwtTrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class JwtTrampolineAuthorizeFilterAutoConfigurationTest {
    private AnnotationConfigApplicationContext context;
    private TokenService tokenService;

    @Before
    public void setUp() throws Exception {
        this.tokenService = mock(TokenService.class);
        context = new AnnotationConfigApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void registersJwtTrampolineAuthorizeFilterAutomatically() {
        this.context.registerBean(JwtTrampolineAuthorizeFilterAutoConfiguration.class, tokenService);
        this.context.refresh();
        TrampolineAuthorizeFilter filter = context.getBean(TrampolineAuthorizeFilter.class);
        assertThat(filter, instanceOf(JwtTrampolineAuthorizeFilter.class));
    }

    @Test
    public void customTrampolineAuthorizeFilterBean() {
        this.context.register(CustomTrampolineAuthorizeFilterConfig.class);
        this.context.registerBean(JwtTrampolineAuthorizeFilterAutoConfiguration.class, tokenService);
        this.context.refresh();
        TrampolineAuthorizeFilter filter = context.getBean(TrampolineAuthorizeFilter.class);
        assertThat(filter, not(instanceOf(JwtTrampolineAuthorizeFilter.class)));
    }


    //Useless config to override autoconfig
    @Configuration
    protected static class CustomTrampolineAuthorizeFilterConfig {
        @Bean
        public TrampolineAuthorizeFilter trampolineAuthorizeFilter() {
            return new TrampolineAuthorizeFilter() {
                @Override
                public void processAuthorization(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

                }
            };
        }
    }
}