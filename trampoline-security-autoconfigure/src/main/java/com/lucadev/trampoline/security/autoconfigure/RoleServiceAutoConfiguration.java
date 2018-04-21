package com.lucadev.trampoline.security.autoconfigure;

import com.lucadev.trampoline.security.repository.RoleRepository;
import com.lucadev.trampoline.security.service.RoleService;
import com.lucadev.trampoline.security.service.impl.RoleServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(RoleService.class)
public class RoleServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleServiceImpl(roleRepository);
    }

}
