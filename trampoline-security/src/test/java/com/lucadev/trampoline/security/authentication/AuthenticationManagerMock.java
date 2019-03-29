package com.lucadev.trampoline.security.authentication;

import com.lucadev.trampoline.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;

/**
 * Fake/mocked {@link AuthenticationManager} to test the authentication services using them
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@AllArgsConstructor
public class AuthenticationManagerMock implements AuthenticationManager {

    private String acceptedUsername;
    private String acceptedPassword;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";
        if (acceptedUsername.equals(username) && acceptedPassword.equals(password)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(username + "@test.junit");
            return new UsernamePasswordAuthenticationToken(user, password,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        }
        throw new BadCredentialsException("1000");
    }
}
