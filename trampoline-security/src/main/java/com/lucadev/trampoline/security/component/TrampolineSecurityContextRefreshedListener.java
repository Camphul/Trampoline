package com.lucadev.trampoline.security.component;

import com.lucadev.trampoline.security.configuration.AuthorizationSchemeConfiguration;
import com.lucadev.trampoline.security.service.AuthorizationSchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

/**
 * Invoke the {@link AuthorizationSchemeConfiguration} on startup.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Configuration
public class TrampolineSecurityContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>, Ordered {

	/**
	 * Order on which listener gets invoked first.
	 */
	public static final int TRAMPOLINE_SECURITY_CONTEXT_REFRESHED_LISTENER_ORDER = 10;
	private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineSecurityContextRefreshedListener.class);

	private final AuthorizationSchemeConfiguration authorizationSchemeConfiguration;
	private final AuthorizationSchemeService authorizationSchemeService;
	private final boolean runConfiguration;

	public TrampolineSecurityContextRefreshedListener(AuthorizationSchemeConfiguration authorizationSchemeConfiguration,
													  AuthorizationSchemeService authorizationSchemeService,
													  @Value("${trampoline.security.authorization.schema.run.configuration:false}") boolean runConfiguration) {
		this.authorizationSchemeConfiguration = authorizationSchemeConfiguration;
		this.authorizationSchemeService = authorizationSchemeService;
		this.runConfiguration = runConfiguration;
	}

	/**
	 * Run the configured {@link AuthorizationSchemeConfiguration}
	 *
	 * @param contextRefreshedEvent the {@link ContextRefreshedEvent} which was fired.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (runConfiguration) {
			LOGGER.debug("ContextRefreshedEvent triggered: running {}", authorizationSchemeConfiguration.getClass().getName());
			authorizationSchemeConfiguration.build(authorizationSchemeService.builder());
		} else {
			LOGGER.debug("AuthorizationSchemeConfiguration is disabled. Not running configuration.");
		}
	}

	/**
	 * Configures the order of which listener get triggered first.
	 *
	 * @return 90 as we are simply a library.
	 */
	@Override
	public int getOrder() {
		return TRAMPOLINE_SECURITY_CONTEXT_REFRESHED_LISTENER_ORDER;
	}
}
