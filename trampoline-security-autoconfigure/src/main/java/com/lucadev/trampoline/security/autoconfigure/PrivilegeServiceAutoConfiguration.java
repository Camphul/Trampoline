package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.impl.PrivilegeServiceImpl;
import com.lucadev.trampoline.security.repository.PrivilegeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(PrivilegeService.class)
public class PrivilegeServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PrivilegeService privilegeService(PrivilegeRepository repository) {
        return new PrivilegeServiceImpl(repository);
    }

}
