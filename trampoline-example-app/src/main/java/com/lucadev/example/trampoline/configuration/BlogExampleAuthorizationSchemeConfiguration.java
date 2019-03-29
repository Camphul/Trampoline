package com.lucadev.example.trampoline.configuration;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import com.lucadev.trampoline.security.configuration.AuthorizationSchemeConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * This is a Trampoline configuration implementation.
 * Implementing {@link AuthorizationSchemeConfiguration} allows you to build the authorization scheme(roles/privileges)
 * using a sort of builder pattern.
 * <p>
 * This configuration is ran when the application started(trampoline has an inner ContextRefreshed listener which has a high priority).
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Configuration
public class BlogExampleAuthorizationSchemeConfiguration implements AuthorizationSchemeConfiguration {

    /**
     * Build the authorization scheme
     *
     * @param builder please see {@link AuthorizationSchemeBuilder} for more information.
     */
    @Override
    public void build(AuthorizationSchemeBuilder builder) {
        //Wrap creation inside system authentication(you most likely wont need this for this purpose).
        builder.wrapSystemAuthentication((wrappedBuilder) ->
                //Create user role
                wrappedBuilder.createRole("ROLE_USER").withPrivileges("GET_WHOAMI", "GET_PING_PROTECTED").buildAnd()
                        //Create admin role
                        .createRole("ROLE_ADMIN").withPrivileges("MANAGE_USERS").buildAnd()
                        //Only do the following when dev profile is enabled
                        .forProfile("dev", (devBuilder) ->
                                //Add developer role
                                devBuilder.createRole("ROLE_DEVELOPER").withPrivilege("DEVELOPER_ACCESS").buildAnd()
                        )
        );
    }
}
