package com.lucadev.trampoline.security.jwt.authorization;

import com.lucadev.trampoline.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter which validates and authorizes the JWT token.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 11-5-18
 */
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private static final Logger JWT_LOGGER = LoggerFactory
			.getLogger(JwtAuthorizationFilter.class);

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			Optional<Authentication> token = tokenService
					.getAuthenticationToken(httpServletRequest);

			if (!token.isPresent()) {
				JWT_LOGGER.debug("Could not authenticate null JWT token.");
				filterChain.doFilter(httpServletRequest, httpServletResponse);
				return;
			}

			Authentication authentication = authenticationManager
					.authenticate(token.get());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			JWT_LOGGER.debug("Set the authentication object.");
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
		catch (Exception ex) {
			SecurityContextHolder.clearContext();
			JWT_LOGGER.info("Failed JWT filter: {}: {}", ex.getClass().getName(),
					ex.getMessage());
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
	}

}
