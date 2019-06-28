package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import com.lucadev.trampoline.security.service.AuthorizationSchemeService;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

/**
 * Default {@link AuthorizationSchemeService} which does a NOP operation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Slf4j
@RequiredArgsConstructor
public class TrampolineAuthorizationSchemeService implements AuthorizationSchemeService {

	private final Environment environment;

	private final RoleService roleService;

	private final PrivilegeService privilegeService;

	@Override
	public AuthorizationSchemeBuilder builder() {
		log.debug("Creating builder...");
		return AuthorizationSchemeBuilder.create(this.roleService, this.privilegeService,
				this.environment);
	}

}
