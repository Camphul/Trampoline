package com.lucadev.trampoline.security.configuration;

import com.lucadev.trampoline.security.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class TrampolineWebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;
    //Request filter for auth
    private final TrampolineAuthorizeFilter trampolineAuthorizeFilter;
    private final AuthenticationEntryPoint entryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        "/auth/**"
                )

                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and()
                .ignoring()
                .antMatchers("/console/**/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(entryPoint).and()

                .authorizeRequests()

                // Un-secure H2 Database
                .antMatchers("/console/**/**").permitAll()

                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();

        http
                .addFilterBefore(trampolineAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // disable page caching
        http
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();

        //Handle config for specific profiles
        for (String profile : environment.getActiveProfiles()) {
            if (profile.equals("dev")) {
                configureDevProfileSecurity(http);
            }
        }
    }

    /**
     * Configure HttpSecurity for dev profile
     *
     * @param http
     * @throws Exception
     */
    private void configureDevProfileSecurity(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/console/**").permitAll()
                .and()
                .headers().frameOptions().disable();
    }

    /**
     * Strictly used in dev profile to load h2 console
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
