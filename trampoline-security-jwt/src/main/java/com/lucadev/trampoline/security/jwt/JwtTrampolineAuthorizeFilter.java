package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.exception.AuthenticationException;
import com.lucadev.trampoline.security.jwt.configuration.JwtProperties;
import com.lucadev.trampoline.security.jwt.model.TokenData;
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
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    public void processAuthorization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!containsValidTokenHeader(request)) {
            return;
        }
        TokenData tokenData = jwtService.getTokenDataFromRequest(request);
        if (tokenData == null) {
            throw new AuthenticationException("Could not obtain token data.");
        }
        User userDetails = authorize(tokenData);
        if (userDetails == null) {
            throw new AuthenticationException("Failed authorization check");
        } else {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    protected boolean containsValidTokenHeader(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getTokenHeader());
        if (token == null || !token.startsWith(jwtProperties.getTokenHeaderPrefix())) {
            return false;
        }
        return true;
    }

    /**
     * Authorize from TokenData
     *
     * @param tokenData
     * @return
     */
    @Transactional
    protected User authorize(TokenData tokenData) {
        if (tokenData.getUsername() != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            String username = tokenData.getUsername();
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            if (!(userDetails instanceof User)) {
                throw new IllegalStateException("UserDetails should be of type User");
            }

            User user = (User) userDetails;


            if (jwtService.isValidToken(tokenData, user)) {
                return userService.updateLastSeen(user);
            }

        }
        throw new AuthenticationException("Could not authenticate using token");
    }
}
