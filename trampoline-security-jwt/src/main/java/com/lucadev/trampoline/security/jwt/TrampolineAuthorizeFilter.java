package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public abstract class TrampolineAuthorizeFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrampolineAuthorizeFilter.class);

    /**
     * Implementation of the filter which catches any exceptions around the custom processing part
     *
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
        } catch (AuthenticationException ex) {
            LOGGER.trace("Failed to process authorization", ex);
            ex.printStackTrace();
        } finally {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    /**
     * Process an incoming request
     *
     * @param request
     * @param response
     * @throws AuthenticationException when we could not getUser/authenticate the request.
     */
    public abstract void processAuthorization(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException;
}
