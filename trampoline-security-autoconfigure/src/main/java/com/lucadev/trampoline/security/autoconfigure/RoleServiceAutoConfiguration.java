package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.persistence.repository.RoleRepository;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.impl.TrampolineRoleService;
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
@ConditionalOnClass(RoleService.class)
public class RoleServiceAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public RoleService roleService(RoleRepository roleRepository) {
		return new TrampolineRoleService(roleRepository);
	}

}
