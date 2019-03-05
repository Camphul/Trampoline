package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.configuration.AuthenticationProperties;
import com.lucadev.trampoline.security.jwt.JwtPayload;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserPasswordService;
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
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 28-4-18
 */
@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);
    private final TokenService tokenService;
    private final UserService userService;
    private final UserPasswordService userPasswordService;
    private final AuthenticationProperties authenticationProperties;

    /**
     * Perform authentication
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        LOGGER.debug("Checking authentication for JwtAuthenticationToken");
        if (authentication instanceof JwtAuthenticationToken) {
            return createJwtAuthentication(((JwtAuthenticationToken) authentication).getJwtPayload());
        } else {
            return createJwtAuthentication(authentication);
        }
    }

    /**
     * Authorize when already owner of a JWT token
     *
     * @param jwtPayload
     * @return
     */
    private Authentication createJwtAuthentication(JwtPayload jwtPayload) {
        String userIdentifier = jwtPayload.getUsername();
        if(authenticationProperties.isEmailIdentification()) {
            userIdentifier = jwtPayload.getEmail();
        }
        UserDetails userDetails = userService.loadUserByIdentifier(userIdentifier);
        User user = (User)userDetails;
        validateToken(userDetails, jwtPayload);
        //Update lastseen
        userService.updateLastSeen(user);
        return new JwtAuthenticationToken(userDetails.getAuthorities(), user, jwtPayload);
    }

    /**
     * Validate the token against the actual user
     *
     * @param userDetails
     * @param jwtPayload
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
     * Authenticate
     *
     * @param authentication
     * @return
     */
    private Authentication createJwtAuthentication(Authentication authentication) {
        User user = getUser(authentication);
        String token = tokenService.createToken(user);
        JwtPayload payload = tokenService.getTokenData(token);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new JwtAuthenticationToken(authorities, user, payload);
    }

    /**
     * Get user from authentication
     *
     * @param authentication
     * @return
     */
    private User getUser(Authentication authentication) {
        String name = authentication.getName();
        String credentials = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userService.loadUserByIdentifier(name);
        if (userDetails == null || !(userDetails instanceof User)) {
            throw new AuthenticationServiceException("Unsupported or null UserDetails Type");
        }

        User user = (User) userDetails;
        boolean correctCredentials = userPasswordService.isPassword(user, credentials);
        if (correctCredentials) {
            return user;
        }
        throw new BadCredentialsException("Invalid credentials?");

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass) ||
                UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
