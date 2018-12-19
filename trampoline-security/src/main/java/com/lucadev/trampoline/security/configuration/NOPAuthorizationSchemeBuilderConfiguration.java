package com.lucadev.trampoline.security.configuration;

import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NOP config.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 7-12-18
 */
public class NOPAuthorizationSchemeBuilderConfiguration implements AuthorizationSchemeBuilderConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(NOPAuthorizationSchemeBuilderConfiguration.class);

    @Override
    public void build(AuthorizationSchemeBuilder builder) {
        //NOP
        LOGGER.debug("No AuthorizationSchemeBuilderConfiguration has been defined. Skipping.");
    }
}
