package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.configuration.AuthorizationSchemeConfiguration;
import com.lucadev.trampoline.security.service.AuthorizationSchemeService;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.impl.TrampolineAuthorizationSchemeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Autoconfigure the service to configure roles and privileges.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Configuration
@ConditionalOnBean(AuthorizationSchemeConfiguration.class)
@ConditionalOnClass(AuthorizationSchemeService.class)
@AllArgsConstructor
public class AuthorizationSchemeServiceAutoConfiguration {

	private final AuthorizationSchemeConfiguration authorizationSchemeConfiguration;

	private final Environment environment;

	private final RoleService roleService;

	private final PrivilegeService privilegeService;

	@Bean
	@ConditionalOnMissingBean(AuthorizationSchemeService.class)
	public AuthorizationSchemeService authorizationSchemeService() {
		AuthorizationSchemeService service = new TrampolineAuthorizationSchemeService(
				this.environment, this.roleService, this.privilegeService);
		this.authorizationSchemeConfiguration.build(service.builder());
		return service;
	}

}
