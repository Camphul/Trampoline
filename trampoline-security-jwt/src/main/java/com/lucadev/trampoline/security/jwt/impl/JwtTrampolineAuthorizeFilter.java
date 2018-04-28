package com.lucadev.trampoline.security.jwt.impl;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import com.lucadev.trampoline.security.jwt.JwtAuthenticationToken;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.TrampolineAuthorizeFilter;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.jwt.model.JwtPayload;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

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

    private final TokenService tokenService;

    @Override
    public void processAuthorization(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = tokenService.getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
