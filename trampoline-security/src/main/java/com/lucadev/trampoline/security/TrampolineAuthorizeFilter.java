package com.lucadev.trampoline.security;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public abstract class TrampolineAuthorizeFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            processAuthorization(httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            throw new AuthenticationException(ex.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public abstract void processAuthorization(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
