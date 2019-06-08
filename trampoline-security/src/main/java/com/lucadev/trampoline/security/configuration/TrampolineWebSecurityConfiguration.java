package com.lucadev.trampoline.security.configuration;

import com.lucadev.trampoline.security.annotation.IgnoreSecurity;
import com.lucadev.trampoline.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.security.PermitAll;

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
	private final RequestMappingHandlerMapping handlerMapping;

	/**
	 * Construct configuration.
	 * @param debug if we should enable debug on websecurity
	 */
	public TrampolineWebSecurityConfiguration(
			@Value("${trampoline.debug.spring.security:false}") boolean debug,
			RequestMappingHandlerMapping handlerMapping) {
		this.debug = debug;
		this.handlerMapping = handlerMapping;
	}

	@Override
	public void init(WebSecurity web) throws Exception {
		web.debug(this.debug);
		configureIgnoredRoutes(web);
	}

	/**
	 * Configures routes annotated with IgnoreSecurity.
	 * @param web the web security.
	 */
	private void configureIgnoredRoutes(WebSecurity web) {
		//Get all mappings.
		handlerMapping.getHandlerMethods().forEach((info,method) -> {
			//If @IgnoreSecurity is present on the method.
			if(method.hasMethodAnnotation(IgnoreSecurity.class)) {
				//Loop through
				info.getPatternsCondition().getPatterns().forEach(pattern -> {
					//For each request method accepted
					info.getMethodsCondition().getMethods().forEach(requestMethod -> {
						//Ignore the security chain for the mapping.
						web.ignoring().antMatchers(HttpMethod.valueOf(requestMethod.name()), pattern);

						//Do some logging
						log.info("Ignoring security chain on method {}#{} with mapping {} {}", method.getMethod().getDeclaringClass().getName(),
								method.getMethod().getName(), requestMethod.name(), pattern);
					});
				});
			}
		});
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
