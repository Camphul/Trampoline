package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.data.exception.ResourceNotFoundException;
import com.lucadev.trampoline.security.authentication.IdentificationType;
import com.lucadev.trampoline.security.jwt.JwtPayload;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserAuthenticationService;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * A {@link AuthenticationProvider} implementation to verify and create JWT's.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 28-4-18
 */
@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
    private final TokenService tokenService;
    private final UserService userService;
    private final UserAuthenticationService userAuthenticationService;

    /**
     * Perform authentication
     *
     * @param authentication the {@link Authentication} object.
     * @return a new {@link Authentication} object based on the old one.
     * @throws AuthenticationException when auth fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        LOGGER.debug("Checking authentication for JwtAuthenticationToken");
        //If there's already a JwtAuthenticationToken present
        if (authentication instanceof JwtAuthenticationToken) {
            JwtPayload payload = ((JwtAuthenticationToken) authentication).getJwtPayload();
            return createValidatedJwtAuthentication(payload);
        } else {
            return createNewJwtAuthentication(authentication);
        }
    }

    /**
     * Obtains either the username of email based upon the {@link IdentificationType}
     * @param payload the {@link JwtPayload}
     * @param identificationType if we use the username or password.
     * @return the username or password.
     */
    private String getUserIdentifier(JwtPayload payload, IdentificationType identificationType) {
        return identificationType == IdentificationType.USERNAME ? payload.getUsername() : payload.getEmail();
    }

    /**
     * Loads {@link UserDetails} from the {@link JwtPayload}
     * @param jwtPayload the authorization token.
     * @return the {@link UserDetails} obtained from the {@link JwtPayload}
     */
    private User loadUserFromJwt(JwtPayload jwtPayload) {
        UserDetails userDetails = userService.loadUserByUsername(getUserIdentifier(jwtPayload, userService.getIdentificationType()));

        if(!(userDetails instanceof User)) {
            throw new ResourceNotFoundException("Could not find a User. Received the wrong type.");
        }
        return (User)userDetails;
    }

    /**
     * Authorize when already owner of a JWT token
     * @param jwtPayload the JWT data.
     * @return the new {@link Authentication} object
     */
    private Authentication createValidatedJwtAuthentication(JwtPayload jwtPayload) {
        User user = loadUserFromJwt(jwtPayload);
        userAuthenticationService.validateUserState(user);
        validateToken(user, jwtPayload);
        //Updates user's last seen.
        userService.updateLastSeen(user);
        return new JwtAuthenticationToken(user.getAuthorities(), user, jwtPayload);
    }

    /**
     * Authenticate
     *
     * @param authentication the auth object.
     * @return a jwt auth object.
     */
    private Authentication createNewJwtAuthentication(Authentication authentication) {
        User user = getUser(authentication);
        userAuthenticationService.validateUserState(user);
        String token = tokenService.createToken(user);
        JwtPayload payload = tokenService.getTokenData(token);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new JwtAuthenticationToken(authorities, user, payload);
    }

    /**
     * Validate the token against the actual user
     *
     * @param userDetails user to validate.
     * @param jwtPayload token to validate against user.
     */
    private void validateToken(UserDetails userDetails, JwtPayload jwtPayload) {
        if (!(userDetails instanceof User)) {
            throw new BadCredentialsException("Failed authentication: unsupported UserDetails type. Must be of type User");
        }

        User user = (User) userDetails;
        if (!tokenService.isValidToken(jwtPayload, user)) {
            throw new BadCredentialsException("Token did not validate against user with success.");
        }
    }

    /**
     * Get user from authentication
     *
     * @param authentication find user from auth object.
     * @return the resolved user.
     */
    private User getUser(Authentication authentication) {
        String name = authentication.getName();
        String credentials = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userService.loadUserByUsername(name);
        if (!(userDetails instanceof User)) {
            throw new AuthenticationServiceException("Unsupported or null UserDetails Type");
        }

        User user = (User) userDetails;

        boolean correctCredentials = userAuthenticationService.isPassword(user, credentials);
        if (correctCredentials) {
            return user;
        }
        throw new BadCredentialsException("Invalid credentials?");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass) ||
                UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
