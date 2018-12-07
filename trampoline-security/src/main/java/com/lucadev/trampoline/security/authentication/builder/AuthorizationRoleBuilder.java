package com.lucadev.trampoline.security.authentication.builder;

import com.lucadev.trampoline.security.model.Privilege;
import com.lucadev.trampoline.security.model.Role;
import com.lucadev.trampoline.security.service.PrivilegeService;
import com.lucadev.trampoline.security.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * Role builder for {@link AuthorizationSchemeBuilder}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
public class AuthorizationRoleBuilder {

    private final AuthorizationSchemeBuilder authorizationSchemeBuilder;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    private String roleName;
    private List<String> privileges;

    /**
     * Construct without a name
     *
     * @param authorizationSchemeBuilder
     * @param roleService
     * @param privilegeService
     */
    public AuthorizationRoleBuilder(AuthorizationSchemeBuilder authorizationSchemeBuilder, RoleService roleService, PrivilegeService privilegeService) {
        this.authorizationSchemeBuilder = authorizationSchemeBuilder;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.privileges = new ArrayList<>();
    }

    /**
     * Construct with a name.
     *
     * @param authorizationSchemeBuilder
     * @param roleService
     * @param privilegeService
     * @param name
     */
    public AuthorizationRoleBuilder(AuthorizationSchemeBuilder authorizationSchemeBuilder, RoleService roleService, PrivilegeService privilegeService, String name) {
        this(authorizationSchemeBuilder, roleService, privilegeService);
        this.roleName = name;
    }

    /**
     * Set role name to create.
     *
     * @param name
     * @return
     */
    public AuthorizationRoleBuilder withName(String name) {
        this.roleName = name;
        return this;
    }

    /**
     * Add privilege to role.
     * Doesn't create duplicate privileges.
     *
     * @param privilege
     * @return
     */
    public AuthorizationRoleBuilder withPrivilege(String privilege) {
        this.privileges.add(privilege);
        return this;
    }

    /**
     * Add list of privileges to role.
     *
     * @param privileges
     * @return
     * @see {@link Privilege}
     */
    public AuthorizationRoleBuilder withPrivileges(String... privileges) {
        AuthorizationRoleBuilder builder = this;
        for (String privilege : privileges) {
            builder = builder.withPrivilege(privilege);
        }
        return builder;
    }

    /**
     * Add privileges from othe existing role.
     *
     * @param roleName the role to copy privileges from.
     * @return
     */
    public AuthorizationRoleBuilder withExistingRolePrivileges(String roleName) {
        Role role = roleService.find(roleName);
        if (role == null) {
            throw new NullPointerException("Could not add roles from existing role. The role name specified was not found.");
        }

        AuthorizationRoleBuilder roleBuilder = this;

        for (Privilege privilege : role.getPrivileges()) {
            roleBuilder = roleBuilder.withPrivilege(privilege.getName());
        }

        return roleBuilder;
    }

    /**
     * Builds the role. Does not build when role already exists.
     * Also adds all roles.
     *
     * @return
     */
    public AuthorizationRoleBuilder build() {
        if (roleName == null || roleName.isEmpty()) {
            throw new IllegalArgumentException("Cannot create role: name must not be null or empty");
        }
        if (roleService.exists(roleName)) {
            return this;
        }

        Role role = roleService.create(roleName);

        //Add roles
        for (String privilegeStr : privileges) {
            Privilege privilege = privilegeService.find(privilegeStr);

            if (privilege == null) {
                privilege = privilegeService.create(privilegeStr);
            }

            role.getPrivileges().add(privilege);
        }


        roleService.update(role);

        return this;
    }

    /**
     * Return to the {@link AuthorizationSchemeBuilder}
     *
     * @return
     */
    public AuthorizationSchemeBuilder and() {
        return authorizationSchemeBuilder;
    }

    /**
     * Builds and returns to {@link AuthorizationSchemeBuilder}
     * Useful when not done add roles.
     *
     * @return
     */
    public AuthorizationSchemeBuilder buildAnd() {
        return build().and();
    }

}
