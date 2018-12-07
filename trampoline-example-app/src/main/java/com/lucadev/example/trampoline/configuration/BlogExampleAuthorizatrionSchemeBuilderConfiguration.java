package com.lucadev.example.trampoline.configuration;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import com.lucadev.trampoline.security.configuration.AuthorizationSchemeBuilderConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Configuration
public class BlogExampleAuthorizatrionSchemeBuilderConfiguration implements AuthorizationSchemeBuilderConfiguration {

    @Override
    public void build(AuthorizationSchemeBuilder builder) {
        builder.wrapSystemAuthentication((wrappedBuilder) ->
                //Create user role
                wrappedBuilder.createRole("ROLE_USER").withPrivileges("WHOAMI_GET", "PING_PROTECTED").buildAnd()
                        //Create admin role
                        .createRole("ROLE_ADMIN").withPrivileges("MANAGE_USERS").buildAnd()
                        //Only build when dev profile is enabled
                        .forProfile("dev", (devBuilder) ->
                                //Add developer role
                                devBuilder.createRole("ROLE_DEVELOPER").withPrivilege("DEVELOPER_ACCESS").buildAnd()
                        )
        );
    }
}
