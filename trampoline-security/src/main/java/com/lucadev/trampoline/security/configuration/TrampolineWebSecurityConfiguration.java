package com.lucadev.trampoline.security.configuration;

import com.lucadev.trampoline.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.lucadev.trampoline.security.configuration.TrampolineWebSecurityConfiguration.TRAMPOLINE_SECURITY_CONFIGURATION_ORDER;

/**
 * Spring {@link WebSecurityConfigurerAdapter} to configure our own services/routes.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(TRAMPOLINE_SECURITY_CONFIGURATION_ORDER)
public class TrampolineWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * The {@link Order} of this configuration, not 100 to allow default config.
	 */
	public static final int TRAMPOLINE_SECURITY_CONFIGURATION_ORDER = 95;

	private final boolean debug;

	/**
	 * Construct configuration.
	 * @param debug if we should enable debug on websecurity
	 */
	public TrampolineWebSecurityConfiguration(
			@Value("${trampoline.debug.spring.security:false}") boolean debug) {
		this.debug = debug;
	}

	@Override
	public void init(WebSecurity web) throws Exception {
		web.debug(this.debug);
	}


	/**
	 * Configure authentication manager.
	 * @param auth builder for the manager.
	 * @param userService user service.
	 * @param passwordEncoder encoder for passwords.
	 * @throws Exception when we couldn't build the auth manager.
	 */
	@Autowired
	protected void initAuthenticationManager(AuthenticationManagerBuilder auth,
			UserService userService, PasswordEncoder passwordEncoder) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

	/**
	 * Bean for the global {@link AuthenticationManager}
	 * @param builder the auth builder.
	 * @return auth manager.
	 */
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationManagerBuilder builder) {
		return authentication -> builder.getOrBuild().authenticate(authentication);
	}

}
