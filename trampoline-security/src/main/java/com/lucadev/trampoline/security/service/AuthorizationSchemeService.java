package com.lucadev.trampoline.security.service;


import com.lucadev.trampoline.security.authentication.builder.AuthorizationSchemeBuilder;

/**
 * Interface used to manage the authorization scheme
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 27-4-18
 */
public interface AuthorizationSchemeService {

    /**
     * Use java builder pattern for creating authorization scheme.
     *
     * @return the builder
     */
    AuthorizationSchemeBuilder builder();

}
