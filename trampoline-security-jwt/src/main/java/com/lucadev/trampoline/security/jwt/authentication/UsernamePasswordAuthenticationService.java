package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import com.lucadev.trampoline.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 14-4-18
 */
@AllArgsConstructor
@Getter
@Component
public class UsernamePasswordAuthenticationService implements AuthenticationService<UsernamePasswordAuthenticationPayload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsernamePasswordAuthenticationService.class);
    private final AuthenticationManager authenticationManager;

    @Override
    public User authenticate(UsernamePasswordAuthenticationPayload authPayload) {
        String username = authPayload.getUsername();
        String password = authPayload.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Could not authenticate: username or password is null");
        }
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Object principal = auth.getPrincipal();
            if (principal == null) {
                throw new AuthenticationException("Failed authentication(null principal)");
            }

            if (principal instanceof User) {
                LOGGER.debug("Authenticated user \"{}\" with success", username);
                return (User) principal;
            } else {
                throw new AuthenticationException("Unexpected principal type(" + principal.getClass().getName() + "): " + principal);
            }
        } catch (DisabledException e) {
            throw new AuthenticationException("User \"" + username + "\" is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials for user \"" + username + "\"", e);
        }
    }

    /**
     * Get user from current thread
     *
     * @return
     */
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof User) {
            return (User) principal;
        }
        LOGGER.debug("Unknown principal: {}", principal);
        return null;
    }
}
