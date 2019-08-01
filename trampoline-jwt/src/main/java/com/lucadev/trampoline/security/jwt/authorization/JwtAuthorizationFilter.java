package com.lucadev.trampoline.security.jwt.authorization;

import com.lucadev.trampoline.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	/**
	 * Obtain JWT, validate and continue the chain.
	 * @param httpServletRequest http request.
	 * @param httpServletResponse http response.
	 * @param filterChain security chain.
	 * @throws ServletException when an exception is raised by authorizing or by the chain.
	 * @throws IOException when an exception is raised by authorizing or by the chain.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			//Obtain JWT
			Optional<Authentication> token = this.tokenService
					.getAuthenticationToken(httpServletRequest);

			//Check if there even is a token present.
			if (!token.isPresent()) {
				log.warn("Could not authenticate null JWT token.");
				filterChain.doFilter(httpServletRequest, httpServletResponse);
				return;
			}

			//Authenticate the token.
			Authentication authentication = this.authenticationManager
					.authenticate(token.get());

			//Set authentication after obtaining the auth token.
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("Set the authentication object.");
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
		catch (Exception ex) {
			SecurityContextHolder.clearContext();
			log.error("Failed JWT filter: {}: {}", ex.getClass().getName(),
					ex.getMessage());
			throw ex;
		}
	}

}
