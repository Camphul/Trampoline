package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.model.JwtPayload;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserPasswordService;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOGGER.info("Checking authentication for JwtAuthenticationToken");
        if (authentication instanceof JwtAuthenticationToken) {
            return getJwtAuthentication(((JwtAuthenticationToken) authentication).getJwtPayload());
        } else {
            return getJwtAuthentication(authentication);
        }
    }

    private Authentication getJwtAuthentication(JwtPayload jwtPayload) {
        UserDetails userDetails = userService.loadUserByUsername(jwtPayload.getUsername());
        return new JwtAuthenticationToken(userDetails.getAuthorities(), (User) userDetails, jwtPayload);
    }

    private Authentication getJwtAuthentication(Authentication authentication) {
        User user = getUser(authentication);
        String token = tokenService.createToken(user);
        JwtPayload payload = tokenService.getTokenData(token);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new JwtAuthenticationToken(authorities, user, payload);
    }

    private User getUser(Authentication authentication) {
        String name = authentication.getName();
        String credentials = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userService.loadUserByUsername(name);
        if (userDetails == null || !(userDetails instanceof User)) {
            throw new com.lucadev.trampoline.security.exception.AuthenticationException("Unexpected userdetails");
        }

        User user = (User) userDetails;
        boolean correctCredentials = userPasswordService.isPassword(user, credentials);
        if (correctCredentials) {
            return user;
        }
        throw new com.lucadev.trampoline.security.exception.AuthenticationException("Invalid credentials?");

    }

//    /**
//     * Authorize from JwtPayload
//     *
//     * @param jwtPayload
//     * @return
//     */
//    protected User getUser(JwtPayload jwtPayload) {
//        if (jwtPayload.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            // It is not compelling necessary to load the use details from the database. You could also store the information
//            // in the token and read it from it. It's up to you ;)
//            String username = jwtPayload.getUsername();
//            UserDetails userDetails = this.userService.loadUserByUsername(username);
//            if (!(userDetails instanceof User)) {
//                return null;
//            }
//
//            User user = (User) userDetails;
//
//
//            if (tokenService.isValidToken(jwtPayload, user)) {
//                return userService.updateLastSeen(user);
//            }
//
//        }
//        return null;
//    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass) ||
                UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
