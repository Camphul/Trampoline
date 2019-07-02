package com.lucadev.trampoline.security.jwt.autoconfigure;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.authentication.JwtAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.authorization.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
	public static final Class<? extends Filter> JWT_FILTER_BEFORE = UsernamePasswordAuthenticationFilter.class;

	// Request filter for auth
	private final AuthenticationEntryPoint entryPoint;

	private final UserDetailsService userService;

	private final TokenService tokenService;

	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder passwordEncoder;

	private final UserDetailsChecker userDetailsChecker;

	/**
	 * Autowires the {@link AuthenticationManager} builder. Used to build the global
	 * {@link AuthenticationManager}
	 * @param builder the builder for the global {@link AuthenticationManager}
	 */
	@Autowired
	public void initAuthenticationManager(AuthenticationManagerBuilder builder) {
		builder.authenticationProvider(authenticationProvider());
	}

	/**
	 * Construct the {@link JwtAuthenticationProvider} used for authentication.
	 * @return JWT auth provider.
	 */
	protected AuthenticationProvider authenticationProvider() {
		return new JwtAuthenticationProvider(this.tokenService, this.userService,
				this.passwordEncoder, this.userDetailsChecker);
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
				// Sessionless
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// Handle unauthorized/auth exceptions
				.exceptionHandling().authenticationEntryPoint(this.entryPoint).and()
				.authorizeRequests()
				// All other requests should be authenticated
				.anyRequest().authenticated().and()
				// Apply our JWT filter.
				.addFilterBefore(filter(), JWT_FILTER_BEFORE);

		// Disable cross site request forgery since JWT is not vulnerable to CSRF
		http.csrf().disable();
	}

	/**
	 * Create our JWT filter.
	 * @return a {@link JwtAuthorizationFilter}
	 */
	protected Filter filter() {
		return new JwtAuthorizationFilter(this.authenticationManager, this.tokenService);
	}

	@Override
	public int getOrder() {
		return JWT_SECURITY_CONFIGURATION_ORDER;
	}

}
