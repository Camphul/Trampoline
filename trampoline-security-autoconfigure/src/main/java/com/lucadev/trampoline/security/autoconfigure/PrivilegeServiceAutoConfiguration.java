package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.persistence.repository.PrivilegeRepository;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.impl.TrampolinePrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure service to manage privileges.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(PrivilegeService.class)
public class PrivilegeServiceAutoConfiguration {

	private final PrivilegeRepository repository;

	@Bean
	@ConditionalOnMissingBean
	public PrivilegeService privilegeService() {
		return new TrampolinePrivilegeService(this.repository);
	}

}
