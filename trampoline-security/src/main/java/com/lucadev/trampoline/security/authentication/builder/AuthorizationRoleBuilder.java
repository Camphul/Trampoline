package com.lucadev.trampoline.security.authentication.builder;

import com.lucadev.trampoline.security.persistence.entity.Privilege;
import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Role} builder for {@link AuthorizationSchemeBuilder}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
public class AuthorizationRoleBuilder {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthorizationRoleBuilder.class);

	private final AuthorizationSchemeBuilder authorizationSchemeBuilder;

	private final RoleService roleService;

	private final PrivilegeService privilegeService;

	private String roleName;

	private List<String> privileges;

	/**
	 * Construct without a role name
	 * @param authorizationSchemeBuilder the main scheme builder
	 * @param roleService the Role handler
	 * @param privilegeService the Privilege handler
	 */
	public AuthorizationRoleBuilder(AuthorizationSchemeBuilder authorizationSchemeBuilder,
			RoleService roleService, PrivilegeService privilegeService) {
		this.authorizationSchemeBuilder = authorizationSchemeBuilder;
		this.roleService = roleService;
		this.privilegeService = privilegeService;

		// Initialize empty privilege list
		this.privileges = new ArrayList<>();
		LOGGER.debug("Constructed AuthorizationRoleBuilder");
	}

	/**
	 * Construct with an initial role name.
	 * @param authorizationSchemeBuilder the main builder
	 * @param roleService the Role handler
	 * @param privilegeService the Privilege handler
	 * @param name the name for the {@link Role} to create.
	 */
	public AuthorizationRoleBuilder(AuthorizationSchemeBuilder authorizationSchemeBuilder,
			RoleService roleService, PrivilegeService privilegeService, String name) {
		this(authorizationSchemeBuilder, roleService, privilegeService);
		this.roleName = name;
	}

	/**
	 * Set the name for the {@link Role}. The name should start with ROLE_ prefix.
	 * @param name the name for the {@link Role}
	 * @return the builder.
	 */
	public AuthorizationRoleBuilder withName(String name) {
		this.roleName = name;
		return this;
	}

	/**
	 * Add a {@link Privilege} to the {@link Role}
	 * @param privilege the String version of the {@link Privilege}
	 * @return the role builder.
	 */
	public AuthorizationRoleBuilder withPrivilege(String privilege) {
		LOGGER.debug("Adding privilege: {}", privilege);
		this.privileges.add(privilege);
		return this;
	}

	/**
	 * Add list of {@link Privilege} to {@link Role}.
	 * @param privileges a String array of privilege strings to add.
	 * @return the role builder.
	 * @see Privilege
	 */
	public AuthorizationRoleBuilder withPrivileges(String... privileges) {
		AuthorizationRoleBuilder builder = this;
		for (String privilege : privileges) {
			builder = builder.withPrivilege(privilege);
		}
		return builder;
	}

	/**
	 * Get {@link Privilege} list from existing {@link Role} and add them to this role.
	 * @param roleName name of the existing {@link Role} to get privileges from.
	 * @return the role builder.
	 */
	public AuthorizationRoleBuilder withExistingRolePrivileges(String roleName) {
		Role role = roleService.find(roleName);
		if (role == null) {
			throw new NullPointerException(
					"Could not add roles from existing role. The role name specified was not found.");
		}

		LOGGER.debug("Adding privileges from existing role with name: {}", roleName);
		AuthorizationRoleBuilder roleBuilder = this;

		for (Privilege privilege : role.getPrivileges()) {
			roleBuilder = roleBuilder.withPrivilege(privilege.getName());
		}

		return roleBuilder;
	}

	/**
	 * Builds the role. Does not build when role already exists. Also adds all roles.
	 * @return the builder.
	 */
	public AuthorizationRoleBuilder build() {
		if (roleName == null || roleName.isEmpty()) {
			throw new IllegalArgumentException(
					"Cannot create role: name must not be null or empty");
		}
		if (roleService.exists(roleName)) {
			return this;
		}

		LOGGER.debug("Building Role from builder.");
		Role role = roleService.create(roleName);

		// Add roles
		for (String privilegeStr : privileges) {
			Privilege privilege = privilegeService.find(privilegeStr);

			if (privilege == null) {
				LOGGER.debug("Creating non-existent privilege: {}", privilegeStr);
				privilege = privilegeService.create(privilegeStr);
			}

			LOGGER.debug("Adding privilege {} to role {}", privilegeStr, roleName);
			role.getPrivileges().add(privilege);
		}

		LOGGER.debug("Persisting updated role");
		roleService.update(role);
		return this;
	}

	/**
	 * Get back to the {@link AuthorizationSchemeBuilder} To persist the built role you
	 * must first invoke {@link #build()} or use {@link #buildAnd()}
	 * @return the origin of this role builder.
	 */
	public AuthorizationSchemeBuilder and() {
		return authorizationSchemeBuilder;
	}

	/**
	 * Wrap {@link #build()} and {@link #and()}
	 * @return the origin of this role builder.
	 */
	public AuthorizationSchemeBuilder buildAnd() {
		return build().and();
	}

}
