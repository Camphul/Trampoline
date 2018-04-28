package com.lucadev.trampoline.security.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import java.util.List;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 28-4-18
 */
public class TrampolineAuthenticationManager extends ProviderManager {

    public TrampolineAuthenticationManager(List<AuthenticationProvider> providers, AuthenticationManager parent) {
        super(providers, parent);
    }
}
