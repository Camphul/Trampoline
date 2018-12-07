package com.lucadev.trampoline.security.authentication.builder;

import com.lucadev.trampoline.security.authentication.SystemAuthentication;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Function;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
public class AuthorizationSchemeBuilder {

    private static final Logger LOGGER = LogManager.getLogger(AuthorizationSchemeBuilder.class);

    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    private final Environment environment;

    private AuthorizationSchemeBuilder(RoleService roleService, PrivilegeService privilegeService, Environment environment) {
        if (roleService == null || privilegeService == null) {
            throw new NullPointerException("This build requires role service and privilege service to not be null.");
        }

        if (environment == null) {
            throw new NullPointerException("This build requires an environement to be set");
        }

        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.environment = environment;
        LOGGER.debug("Constructed AuthorizationSchemeBuilder");
    }

    /**
     * Construct a builder.
     *
     * @param roleService
     * @param privilegeService
     * @return
     */
    public static AuthorizationSchemeBuilder create(RoleService roleService, PrivilegeService privilegeService, Environment environment) {
        return new AuthorizationSchemeBuilder(roleService, privilegeService, environment);
    }

    /**
     * Wrap calls inside set authentication
     *
     * @param authentication
     * @param wrapped
     * @return
     */
    public AuthorizationSchemeBuilder wrapAuthentication(Authentication authentication,
                                                         Function<AuthorizationSchemeBuilder, AuthorizationSchemeBuilder> wrapped) {

        AuthorizationSchemeBuilder resultBuilder = this;
        try {
            LOGGER.debug("Wrapping inside authentication");
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            ctx.setAuthentication(authentication);
            resultBuilder = wrapped.apply(this);
        } finally {
            LOGGER.debug("Clearing security context");
            SecurityContextHolder.clearContext();
        }
        return resultBuilder;
    }

    /**
     * Wrap calls inside {@link SystemAuthentication}
     *
     * @param wrapped
     * @return
     */
    public AuthorizationSchemeBuilder wrapSystemAuthentication(Function<AuthorizationSchemeBuilder, AuthorizationSchemeBuilder> wrapped) {
        return wrapAuthentication(new SystemAuthentication(), wrapped);
    }

    /**
     * Only when the profile is enabled
     *
     * @param profile
     * @param wrapped
     * @return
     */
    public AuthorizationSchemeBuilder forProfile(String profile, Function<AuthorizationSchemeBuilder, AuthorizationSchemeBuilder> wrapped) {
        return forProfiles(new String[]{profile}, wrapped);
    }

    /**
     * Only when all of the profiles is enabled
     *
     * @param profiles
     * @param wrapped
     * @return
     */
    public AuthorizationSchemeBuilder forProfiles(String[] profiles, Function<AuthorizationSchemeBuilder, AuthorizationSchemeBuilder> wrapped) {
        boolean hasProfiles = true;

        for (String profile : profiles) {
            boolean passes = false;
            //Check if requested profile is within active profiles
            for (String activeProfile : environment.getActiveProfiles()) {
                if (profile.equals(activeProfile)) {
                    passes = true;
                }
            }
            //If not, it wont pass
            if (!passes) {
                LOGGER.debug("Profile check for auth scheme builder did not pass profile: {}", profile);
                hasProfiles = false;
                break;
            }
        }
        AuthorizationSchemeBuilder resultBuilder = this;

        if (hasProfiles) {
            LOGGER.debug("All profiles requested pass");
            resultBuilder = wrapped.apply(this);
        }
        return resultBuilder;
    }

    /**
     * Create role
     *
     * @param name
     * @return
     */
    public AuthorizationRoleBuilder createRole(String name) {
        return new AuthorizationRoleBuilder(this, roleService, privilegeService, name);
    }

    /**
     * Create role
     *
     * @return
     */
    public AuthorizationRoleBuilder createRole() {
        return new AuthorizationRoleBuilder(this, roleService, privilegeService);
    }
}
