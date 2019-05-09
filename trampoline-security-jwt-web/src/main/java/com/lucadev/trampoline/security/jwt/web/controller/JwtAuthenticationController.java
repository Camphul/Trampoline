package com.lucadev.trampoline.security.jwt.web.controller;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.web.model.JwtAuthenticationResponse;
import com.lucadev.trampoline.security.jwt.web.model.UserAuthenticationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * REST model for JWT auth related endpoints.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@RestController
@RequestMapping("${trampoline.security.jwt.authPath:/auth}")
@AllArgsConstructor
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	/**
	 * Login with username-password.
	 * @param userAuthenticationRequest authorization request dto
	 * @return response of the authorization
	 */
	@PostMapping("${trampoline.security.jwt.authPath.authorize:/authorize}")
	public JwtAuthenticationResponse submitAuthenticationTokenRequest(
			@Valid @RequestBody UserAuthenticationRequest userAuthenticationRequest) {
		try {
			Authentication authentication = this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							userAuthenticationRequest.getIdentifier(),
							userAuthenticationRequest.getPassword()));

			if (authentication == null) {
				throw new AccessDeniedException("Could not authorize user.");
			}
			return new JwtAuthenticationResponse(
					String.valueOf(authentication.getDetails()));
		}
		catch (Exception ex) {
			throw new AccessDeniedException("Could not authorize: " + ex.getMessage());
		}
	}

	/**
	 * Refresh token from current logged in request.
	 * @param request http req
	 * @param response http resp
	 * @return jwt auth response.
	 */
	@GetMapping("${trampoline.security.jwt.authPath.refresh:/refresh}")
	public JwtAuthenticationResponse submitAuthenticationTokenRefreshRequest(
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String refreshedToken = this.tokenService.refreshTokenFromRequest(request);
			return new JwtAuthenticationResponse(refreshedToken);
		}
		catch (Exception e) {
			// Catch model to always return correct format
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			throw new AccessDeniedException("Could not refresh token: " + e.getMessage());
		}
	}

}
