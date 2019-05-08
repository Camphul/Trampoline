package com.lucadev.trampoline.security.jwt.configuration;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.authentication.JwtAuthenticationProvider;
import com.lucadev.trampoline.security.jwt.authorization.JwtAuthorizationFilter;
import com.lucadev.trampoline.security.service.UserAuthenticationService;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

import static com.lucadev.trampoline.security.jwt.configuration.JwtWebSecurityConfiguration.JWT_SECURITY_CONFIGURATION_ORDER;


/**
 * Have 2 configurations, in this one we configure the coupled parts such as auth path
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@EnableConfigurationProperties(JwtSecurityProperties.class)
@AllArgsConstructor
@Order(JWT_SECURITY_CONFIGURATION_ORDER)
public class JwtWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * The {@link Order} of this configuration
	 */
	public static final int JWT_SECURITY_CONFIGURATION_ORDER = 50;
	/**
	 * The filter class which our custom JWT filter will sit infront of
	 */
	public static final Class<? extends Filter> JWT_FILTER_BEFORE = UsernamePasswordAuthenticationFilter.class;


	private final JwtSecurityProperties jwtSecurityProperties;
	//Request filter for auth
	private final AuthenticationEntryPoint entryPoint;
	private final UserAuthenticationService userAuthenticationService;
	private final UserService userService;
	private final TokenService tokenService;
	private final AuthenticationManager authenticationManager;

	/**
	 * Websecurity to allow auth route.
	 *
	 * @param web web security.
	 * @throws Exception when we fail to configure.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers(
						HttpMethod.POST,
						jwtSecurityProperties.getAuthPath() + "/**"
				);
	}

	/**
	 * Autowires the {@link AuthenticationManager} builder. Used to build the global {@link AuthenticationManager}
	 *
	 * @param builder the builder for the global {@link AuthenticationManager}
	 */
	@Autowired
	public void initAuthenticationManager(AuthenticationManagerBuilder builder) {
		builder.authenticationProvider(authenticationProvider());
	}

	/**
	 * Construct the {@link JwtAuthenticationProvider} used for authentication.
	 *
	 * @return JWT auth provider.
	 */
	protected AuthenticationProvider authenticationProvider() {
		return new JwtAuthenticationProvider(tokenService, userService, userAuthenticationService);
	}

	/**
	 * Configure security chains to work with JWT.
	 *
	 * @param http http security.
	 * @throws Exception when we fail to configure http security.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//Sessionless
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				//Handle unauthorized/auth exceptions
				.exceptionHandling().authenticationEntryPoint(entryPoint)
				.and()
				.authorizeRequests()
				//Permit auth requests
				.antMatchers(jwtSecurityProperties.getAuthPath()).permitAll()
				//All other requests should be authenticated
				.anyRequest().authenticated()
				.and()
				//Apply our JWT filter.
				.addFilterBefore(filter(), JWT_FILTER_BEFORE);

		//Disable cross site request forgery since JWT is not vulnerable to CSRF
		http.csrf().disable();
	}

	/**
	 * Create our JWT filter
	 *
	 * @return a {@link JwtAuthorizationFilter}
	 */
	protected Filter filter() {
		return new JwtAuthorizationFilter(authenticationManager, tokenService);
	}

}
