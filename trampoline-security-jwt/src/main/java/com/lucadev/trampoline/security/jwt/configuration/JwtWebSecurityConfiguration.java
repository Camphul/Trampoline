package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.jwt.JwtAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.service.UserPasswordService;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;


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

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        jwtSecurityProperties.getAuthPath() + "/**"
                );
    }

    @Autowired
    public void initAuthenticationManager(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(jwtAuthenticationProvider());
    }

    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(tokenService, userService, userPasswordService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .antMatchers(jwtSecurityProperties.getAuthPath()).permitAll();

    }

}
