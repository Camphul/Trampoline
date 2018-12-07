package com.lucadev.example.trampoline;

import com.lucadev.trampoline.security.service.AuthorizationSchemeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test component to import test data.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Component
@AllArgsConstructor
public class AuthSchemeImporter implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSchemeImporter.class);
    private final AuthorizationSchemeService authorizationSchemeService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //Wrap inside SystemAuthentication SecurityContext
        authorizationSchemeService.builder().wrapSystemAuthentication((wrappedBuilder) ->
                //Create user role
                wrappedBuilder.createRole("ROLE_USER").withPrivileges("WHOAMI_GET", "PING").buildAnd()
                        //Create admin role
                        .createRole("ROLE_ADMIN").withExistingRolePrivileges("ROLE_USER").withPrivileges("MANAGE_USERS").buildAnd()
                        //Only build when dev profile is enabled
                        .forProfile("dev", (devBuilder) ->
                                //Add developer role
                                devBuilder.createRole("ROLE_DEVELOPER").withPrivilege("DEVELOPER_ACCESS").buildAnd()
                        )
        );
    }

}
