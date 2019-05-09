package com.lucadev.trampoline.security.authentication.builder;

import com.lucadev.trampoline.security.authentication.SystemAuthentication;
import com.lucadev.trampoline.security.persistence.entity.Privilege;
import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Function;

/**
 * Builder to create an authorization scheme. Creates roles and privileges accordingly.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
public final class AuthorizationSchemeBuilder {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthorizationSchemeBuilder.class);

	private final RoleService roleService;

	private final PrivilegeService privilegeService;

	private final Environment environment;

	/**
	 * Private constructor to create the builder.
	 * @param roleService the {@link RoleService} bean
	 * @param privilegeService the {@link PrivilegeService} bean
	 * @param environment the {@link Environment} bean
	 */
	private AuthorizationSchemeBuilder(RoleService roleService,
			PrivilegeService privilegeService, Environment environment) {
		if (roleService == null || privilegeService == null) {
			throw new NullPointerException(
					"This build requires role handler and privilege handler to not be null.");
		}

		if (environment == null) {
			throw new NullPointerException(
					"This build requires an environement to be set");
		}

		this.roleService = roleService;
		this.privilegeService = privilegeService;
		this.environment = environment;
		LOGGER.debug("Constructed AuthorizationSchemeBuilder");
	}

	/**
	 * Construct a builder.
	 * @param roleService the {@link Role} handler bean.
	 * @param privilegeService the {@link Privilege} handler bean.
	 * @param environment current environment.
	 * @return a new builder.
	 */
	public static AuthorizationSchemeBuilder create(RoleService roleService,
			PrivilegeService privilegeService, Environment environment) {
		return new AuthorizationSchemeBuilder(roleService, privilegeService, environment);
	}

	/**
	 * Wrap the builder inside a {@link SecurityContext}. Clears the
	 * {@link SecurityContext} when the wrapped function is done executing.
	 * @param authentication the {@link Authentication} object for the
	 * {@link SecurityContext}
	 * @param wrapped the lambda function to apply when the {@link SecurityContext} is
	 * set.
	 * @return the result builder.
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
		}
		finally {
			LOGGER.debug("Clearing security context");
			SecurityContextHolder.clearContext();
		}
		return resultBuilder;
	}

	/**
	 * Wrap the builder inside a {@link SecurityContext} with {@link SystemAuthentication}
	 * as {@link Authentication} object. Clears the {@link SecurityContext} when the
	 * wrapped function is done executing.
	 * @param wrapped the lambda function to apply when the {@link SecurityContext} is
	 * set.
	 * @return the result builder.
	 */
	public AuthorizationSchemeBuilder wrapSystemAuthentication(
			Function<AuthorizationSchemeBuilder, AuthorizationSchemeBuilder> wrapped) {
		return wrapAuthentication(new SystemAuthentication(), wrapped);
	}

	/**
	 * Execute the wrapped builder only when the specified Spring profile is active.
	 * @param profile the profile required to apply the wrapped function.
	 * @param wrapped the function to apply.
	 * @return the result builder.
	 */
	public AuthorizationSchemeBuilder forProfile(String profile,
			Function<AuthorizationSchemeBuilder, AuthorizationSchemeBuilder> wrapped) {
		return forProfiles(new String[] { profile }, wrapped);
	}

	/**
	 * Execute the wrapped builder only when the specified Spring profiles are active.
	 * @param profiles the profiles required to apply the wrapped function.
	 * @param wrapped the function to apply.
	 * @return the result builder.
	 */
	public AuthorizationSchemeBuilder forProfiles(String[] profiles,
			Function<AuthorizationSchemeBuilder, AuthorizationSchemeBuilder> wrapped) {
		boolean hasProfiles = true;

		for (String profile : profiles) {
			boolean passes = false;
			// Check if requested profile is within active profiles
			for (String activeProfile : this.environment.getActiveProfiles()) {
				if (profile.equals(activeProfile)) {
					passes = true;
				}
			}
			// If not, it wont pass
			if (!passes) {
				LOGGER.debug(
						"Profile check for auth scheme builder did not pass profile: {}",
						profile);
				hasProfiles = false;
				break;
			}
		}
		AuthorizationSchemeBuilder resultBuilder = this;

		if (hasProfiles) {
			LOGGER.debug("All profiles requested pass. Applying builder function.");
			resultBuilder = wrapped.apply(this);
		}
		return resultBuilder;
	}

	/**
	 * Create a {@link Role} using a special builder.
	 * @param name the role name.
	 * @return the {@link Role} builder.
	 */
	public AuthorizationRoleBuilder createRole(String name) {
		return new AuthorizationRoleBuilder(this, this.roleService, this.privilegeService, name);
	}

	/**
	 * Create a {@link Role} using a special builder without setting the role name yet.
	 * @return the {@link Role} builder.
	 */
	public AuthorizationRoleBuilder createRole() {
		return new AuthorizationRoleBuilder(this, this.roleService, this.privilegeService);
	}

}
