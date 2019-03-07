package com.lucadev.trampoline.security.service.impl;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import com.lucadev.trampoline.security.service.AuthorizationSchemeService;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@AllArgsConstructor
public class TrampolineAuthorizationSchemeService implements AuthorizationSchemeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineAuthorizationSchemeService.class);
    private final Environment environment;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    @Override
    public AuthorizationSchemeBuilder builder() {
        LOGGER.debug("Creating builder...");
        return AuthorizationSchemeBuilder.create(roleService, privilegeService, environment);
    }
}
