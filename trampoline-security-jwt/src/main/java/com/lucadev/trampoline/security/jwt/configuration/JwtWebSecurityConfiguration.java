package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.jwt.TrampolineAuthorizeFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Have 2 configurations, in this one we configure the coupled parts such as auth path
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@EnableConfigurationProperties(JwtSecurityProperties.class)
@AllArgsConstructor
@Order(1)
public class JwtWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtSecurityProperties jwtSecurityProperties;
    //Request filter for auth
    private final TrampolineAuthorizeFilter trampolineAuthorizeFilter;
    private final AuthenticationEntryPoint entryPoint;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        jwtSecurityProperties.getAuthPath() + "/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//Our token is invulnerable
        http.authorizeRequests().antMatchers(jwtSecurityProperties.getAuthPath()).permitAll()
                .anyRequest().authenticated();
        http
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http

                .exceptionHandling().authenticationEntryPoint(entryPoint);

        http
                .addFilterBefore(trampolineAuthorizeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
