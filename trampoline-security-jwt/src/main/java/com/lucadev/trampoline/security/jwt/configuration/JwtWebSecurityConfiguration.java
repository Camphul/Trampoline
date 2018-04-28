package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.authentication.TrampolineAuthenticationManager;
import com.lucadev.trampoline.security.jwt.JwtAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.impl.JwtTrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.service.UserPasswordService;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Have 2 configurations, in this one we configure the coupled parts such as auth path
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(JwtSecurityProperties.class)
@AllArgsConstructor
@Order(3)
public class JwtWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtSecurityProperties jwtSecurityProperties;
    //Request filter for auth
    private final AuthenticationEntryPoint entryPoint;
    private final UserPasswordService userPasswordService;
    private final UserService userService;
    private final TokenService tokenService;
    private final TrampolineAuthenticationManager authenticationManager;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        jwtSecurityProperties.getAuthPath() + "/**"
                );
    }

    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(tokenService, userService, userPasswordService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        authenticationManager.getProviders().add(jwtAuthenticationProvider());
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .addFilterBefore(new JwtTrampolineAuthorizeFilter(tokenService), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterBefore(new BasicAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class)
                .authenticationProvider(jwtAuthenticationProvider())
                .authorizeRequests()
                .antMatchers(jwtSecurityProperties.getAuthPath()).permitAll()
                .anyRequest().authenticated();


    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationManager;
    }
}
