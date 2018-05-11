package com.lucadev.trampoline.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 11-5-18
 */
public class JwtAuthorizationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger JWT_LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final TokenService tokenService;

    public JwtAuthorizationFilter(TokenService tokenService) {
        super("/**");
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        JWT_LOGGER.debug("JwtAuthorizationFilter#attemptAuthorization");
        return tokenService.getAuthentication(request);
    }
}
