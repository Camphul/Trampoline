package com.lucadev.trampoline.security;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract filter to handle user authorization
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public abstract class TrampolineAuthorizeFilter extends OncePerRequestFilter{

    /**
     * Implementation of the filter which catches any exceptions around the custom processing part
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            processAuthorization(httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            throw new AuthenticationException(ex.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Process an incoming request
     * @param request
     * @param response
     * @throws Exception
     */
    public abstract void processAuthorization(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
