package com.lucadev.trampoline.security.web.configuration;

import com.lucadev.trampoline.security.web.annotation.IgnoreSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Configuration which ignores security chain based on IgnoreSecurity annotation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@RequiredArgsConstructor
public class IgnoreSecurityWebSecurityConfiguration extends WebSecurityConfigurerAdapter
		implements Ordered {

	/**
	 * Load order for configuration.
	 */
	public static final int IGNORE_SECURITY_CONFIGURATION_ORDER = 80;

	private final RequestMappingHandlerMapping handlerMapping;

	@Override
	public void configure(WebSecurity web) throws Exception {
		configureIgnoredRoutes(web);
	}

	/**
	 * Configures routes annotated with IgnoreSecurity.
	 * @param web the web security.
	 */
	public void configureIgnoredRoutes(WebSecurity web) {
		// Get all mappings.
		this.handlerMapping.getHandlerMethods().forEach((info, method) -> {
			// If @IgnoreSecurity is present on the method.
			if (AnnotationUtils.findAnnotation(method.getMethod(),
					IgnoreSecurity.class) != null) {
				// Loop through
				info.getPatternsCondition().getPatterns().forEach(pattern -> {
					// For each request method accepted
					info.getMethodsCondition().getMethods().forEach(requestMethod -> {
						// Ignore the security chain for the mapping.
						web.ignoring().antMatchers(
								HttpMethod.valueOf(requestMethod.name()), pattern);

						// Do some logging
						log.info(
								"Ignoring security chain on method {}#{} with mapping {} {}",
								method.getMethod().getDeclaringClass().getName(),
								method.getMethod().getName(), requestMethod.name(),
								pattern);
					});
				});
			}
		});
	}

	@Override
	public int getOrder() {
		return IGNORE_SECURITY_CONFIGURATION_ORDER;
	}

}
