package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.exception.AuthenticationException;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.jwt.model.JwtPayload;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Auth filter
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@AllArgsConstructor
public class JwtTrampolineAuthorizeFilter extends TrampolineAuthorizeFilter {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtSecurityProperties jwtSecurityProperties;

    @Override
    public void processAuthorization(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!containsValidTokenHeader(request)) {
            return;
        }
        JwtPayload jwtPayload = tokenService.getTokenDataFromRequest(request);
        if (jwtPayload == null) {
            throw new AuthenticationException("Could not obtain token data.");
        }
        User userDetails = authorize(jwtPayload);
        if (userDetails == null) {
            throw new AuthenticationException("Failed authorization check");
        } else {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    protected boolean containsValidTokenHeader(HttpServletRequest request) {
        String token = request.getHeader(jwtSecurityProperties.getTokenHeader());
        return !(token == null || !token.startsWith(jwtSecurityProperties.getTokenHeaderPrefix()));
    }

    /**
     * Authorize from JwtPayload
     *
     * @param jwtPayload
     * @return
     */
    protected User authorize(JwtPayload jwtPayload) {
        if (jwtPayload.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            String username = jwtPayload.getUsername();
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            if (!(userDetails instanceof User)) {
                throw new IllegalStateException("UserDetails should be of type User");
            }

            User user = (User) userDetails;


            if (tokenService.isValidToken(jwtPayload, user)) {
                return userService.updateLastSeen(user);
            }

        }
        throw new AuthenticationException("Could not authenticate using token");
    }
}
