package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.persistence.repository.RoleRepository;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.impl.TrampolineRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure service for managing roles.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(RoleService.class)
public class RoleServiceAutoConfiguration {

	private final RoleRepository roleRepository;

	@Bean
	@ConditionalOnMissingBean
	public RoleService roleService() {
		return new TrampolineRoleService(this.roleRepository);
	}

}
