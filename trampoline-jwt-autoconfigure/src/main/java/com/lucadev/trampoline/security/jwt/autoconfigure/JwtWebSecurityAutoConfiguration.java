package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.authentication.TokenAuthenticationEntryPoint;
import com.lucadev.trampoline.security.jwt.authentication.TokenAuthenticationFilter;
import com.lucadev.trampoline.security.jwt.authentication.TokenAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.servlet.Filter;

/**
 * Have 2 configurations, in this one we configure the coupled parts such as auth path.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Slf4j
@Configuration
@AutoConfigureAfter({ TokenServiceAutoConfiguration.class, JwtAutoConfiguration.class })
@RequiredArgsConstructor
public class JwtWebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter
		implements Ordered {

	/**
	 * The {@link Order} of this configuration.
	 */
	public static final int JWT_SECURITY_CONFIGURATION_ORDER = 50;

	/**
	 * The filter class which our custom JWT filter will sit infront of.
	 */
	public static final Class<? extends Filter> JWT_FILTER_BEFORE = RequestHeaderAuthenticationFilter.class;

	private final AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService;

	private final TokenService tokenService;

	private TokenAuthenticationProvider tokenAuthenticationProvider;

	private final JwtSecurityConfigurationProperties jwtProperties;

	@Autowired(required = false/*
								 * This mechanism will be replaced soon by a rewritten
								 * adapter module
								 */)
	public void setTokenAuthenticationProvider(
			TokenAuthenticationProvider tokenAuthenticationProvider) {
		this.tokenAuthenticationProvider = tokenAuthenticationProvider;
	}

	/**
	 * Configure security chains to work with JWT.
	 * @param http http security.
	 * @throws Exception when we fail to configure http security.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.debug("Configuring jwt websecurity");
		http
				// Handle unauthorized/auth exceptions
				.authorizeRequests()
				// All other requests should be authenticated
				.anyRequest().authenticated().and()
				// Apply our JWT filter.
				.addFilterBefore(filter(), JWT_FILTER_BEFORE).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
		// Disable cross site request forgery since JWT is not vulnerable to CSRF
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(preAuthenticationProvider());
		if (this.tokenAuthenticationProvider != null) {
			auth.authenticationProvider(this.tokenAuthenticationProvider);
		}
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new TokenAuthenticationEntryPoint();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Create our JWT filter.
	 * @return a {@link TokenAuthenticationFilter}
	 */
	protected Filter filter() {
		TokenAuthenticationFilter filter = new TokenAuthenticationFilter(
				this.jwtProperties);
		try {
			filter.setAuthenticationManager(authenticationManagerBean());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return filter;
	}

	/**
	 * Construct the {@link PreAuthenticatedAuthenticationProvider} used for
	 * authentication.
	 * @return JWT auth provider.
	 */
	@Bean
	protected AuthenticationProvider preAuthenticationProvider() {
		PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
		provider.setPreAuthenticatedUserDetailsService(
				this.authenticationUserDetailsService);
		return provider;
	}

	@Override
	public int getOrder() {
		return JWT_SECURITY_CONFIGURATION_ORDER;
	}

}
