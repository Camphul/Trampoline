package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.model.JwtPayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 28-4-18
 */
@ToString
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private final JwtPayload jwtPayload;

    public JwtAuthenticationToken(JwtPayload jwtPayload) {
        super(new ArrayList<>());
        this.jwtPayload = jwtPayload;
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return jwtPayload.getUsername();
    }

}
