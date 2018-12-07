package com.lucadev.trampoline.security.service;


import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;
import com.lucadev.trampoline.security.model.auth.scheme.AuthorizationSchemeModel;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Interface used to manage the authorization scheme
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface AuthorizationSchemeService {

    /**
     * Load a authorization scheme
     *
     * @param resource
     * @return
     * @throws IOException
     */
    AuthorizationSchemeModel loadModel(Resource resource) throws IOException;

    /**
     * Migrate an authorization scheme into the database.
     */
    void importAuthorizationScheme(AuthorizationSchemeModel authorizationSchemeModel) throws IOException;

    /**
     * Use java builder pattern for creating authorization scheme.
     *
     * @return the builder
     */
    AuthorizationSchemeBuilder builder();

}
