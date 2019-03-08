package com.lucadev.trampoline.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
 * Authentication to bypass the authorization which requires authentication
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 12-5-18
 */
public class SystemAuthentication implements Authentication {

    /**
     * System authorities.
     * @return No authorities in this case.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    /**
     * System credentials
     * @return null
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * System details
     * @return null
     */
    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * System principal
     * @return the authentication name.
     */
    @Override
    public Object getPrincipal() {
        return getName();
    }

    /**
     * If we are authenticated.
     * @return true
     */
    @Override
    public boolean isAuthenticated() {
        return true;
    }

    /**
     * Disallow updating
     * @param b the new state.
     * @throws IllegalArgumentException
     */
    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new IllegalArgumentException("Cannot update authenticated state of SystemAuthentication");
    }

    /**
     * Name of this principal
     * @return SYSTEM.
     */
    @Override
    public String getName() {
        return "SYSTEM";
    }
}
