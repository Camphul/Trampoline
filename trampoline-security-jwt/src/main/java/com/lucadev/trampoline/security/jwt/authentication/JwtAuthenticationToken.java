package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.jwt.JwtPayload;
import com.lucadev.trampoline.security.model.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
 * Authentication token for JWT
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 28-4-18
 */
@ToString
@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final JwtPayload jwtPayload;
    private final Object principal;

    /**
     * Construct the token with raw jwt data. Not setting authenticated.
     *
     * @param jwtPayload the JWT representation.
     */
    public JwtAuthenticationToken(JwtPayload jwtPayload) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.jwtPayload = jwtPayload;
        this.principal = jwtPayload.getUsername();
    }

    /**
     * Construct an authenticated token.
     *
     * @param authorities the user authorities.
     * @param user        the actual user.
     * @param jwtPayload  the jwt linked to the user.
     */
    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, User user, JwtPayload jwtPayload) {
        super(authorities);
        this.jwtPayload = jwtPayload;
        this.principal = user;
        setAuthenticated(true);
    }

    /**
     * Never pass credentials in JWT
     *
     * @return user credentials. NA in JWT.
     */
    @Override
    public Object getCredentials() {
        return "N/A";
    }

    /**
     * {@inheritDoc}
     *
     * @return either username or {@link User} object when authenticated.
     */
    @Override
    public Object getPrincipal() {
        return principal;
    }

    /**
     * Username inside the jwt token.
     *
     * @return JWT username.
     */
    @Override
    public String getName() {
        return jwtPayload.getUsername();
    }

    /**
     * Auth details, jwt token when authenticated. Else {@code super.getDetails();}
     *
     * @return user details.
     */
    @Override
    public Object getDetails() {
        return isAuthenticated() ? jwtPayload.getRawToken() : super.getDetails();
    }

    /**
     * Get the user.
     *
     * @return null if the principal is not a {@link User}
     */
    public User getUser() {
        if (!(principal instanceof User)) {
            return null;
        }
        return (User) principal;
    }
}
