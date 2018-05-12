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
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 28-4-18
 */
@ToString
@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final JwtPayload jwtPayload;
    private final Object principal;

    public JwtAuthenticationToken(JwtPayload jwtPayload) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.jwtPayload = jwtPayload;
        this.principal = jwtPayload.getUsername();
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, User user, JwtPayload jwtPayload) {
        super(authorities);
        this.jwtPayload = jwtPayload;
        this.principal = user;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public String getName() {
        return jwtPayload.getUsername();
    }

    @Override
    public Object getDetails() {
        return isAuthenticated() ? jwtPayload.getRawToken() : super.getDetails();
    }
}
