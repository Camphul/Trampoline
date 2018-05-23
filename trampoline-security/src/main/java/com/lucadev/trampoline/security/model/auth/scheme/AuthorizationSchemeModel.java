package com.lucadev.trampoline.security.model.auth.scheme;

import lombok.Data;

/**
 * POJO of the authorization scheme.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Data
public class AuthorizationSchemeModel {
    private String[] profiles;
    private String[] defaultRoles;
    private PrivilegeAuthorizationSchemeModel[] privileges;
    private RoleAuthorizationSchemeModel[] roles;

    /**
     * Privilege model
     */
    @Data
    public static class PrivilegeAuthorizationSchemeModel {
        private String name;
    }

    /**
     * Role model
     */
    @Data
    public static class RoleAuthorizationSchemeModel {
        private String name;
        private String[] privileges;
        private String[] parents;
    }

}