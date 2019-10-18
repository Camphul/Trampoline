package com.lucadev.trampoline.security.jwt.support.web.controller;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.support.web.model.TokenAuthenticationResponse;
import com.lucadev.trampoline.security.jwt.support.web.model.UserAuthenticationRequest;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.security.web.annotation.IgnoreSecurity;
import com.lucadev.trampoline.security.web.model.UserSummaryDto;
import com.lucadev.trampoline.security.web.model.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@ConditionalOnMissingBean(value = AuthenticationController.class,
		ignored = JwtAuthenticationController.class)
@RequestMapping("${trampoline.security.jwt.web.baseMapping:/auth}")
@AllArgsConstructor
public class JwtAuthenticationController implements
		AuthenticationController<TokenAuthenticationResponse, UserAuthenticationRequest, UserSummaryDto> {

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	private final UserService userService;

	private final UserMapper userMapper;

	/**
	 * Login with username-password.
	 * @param userAuthenticationRequest authorization request dto
	 * @return response of the authorization
	 */
	@IgnoreSecurity
	@PostMapping("${trampoline.security.jwt.web.authorizeMapping:/authorize}")
	@Override
	public TokenAuthenticationResponse authenticate(
			@Valid @RequestBody UserAuthenticationRequest userAuthenticationRequest) {
		try {
			Authentication authentication = this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							userAuthenticationRequest.getIdentifier(),
							userAuthenticationRequest.getPassword()));

			if (authentication == null) {
				throw new AccessDeniedException("Could not authorize user.");
			}
			return new TokenAuthenticationResponse(
					String.valueOf(authentication.getDetails()));
		}
		catch (Exception ex) {
			throw new AccessDeniedException(ex.getMessage());
		}
	}

	/**
	 * Refresh token from current logged in request.
	 * @param request http request.
	 * @param response http response.
	 * @return jwt auth response.
	 */
	@Override
	@GetMapping("${trampoline.security.jwt.web.refreshMapping:/refresh}")
	public TokenAuthenticationResponse refresh(HttpServletRequest request,
											   HttpServletResponse response) {
		try {
			String refreshedToken = this.tokenService.issueTokenRefresh(request);
			return new TokenAuthenticationResponse(refreshedToken);
		}
		catch (Exception e) {
			// Catch model to always return correct format
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			throw new AccessDeniedException("Could not refresh token: " + e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping("${trampoline.security.jwt.web.userMapping:/user}")
	public UserSummaryDto user(HttpServletRequest request) {
		User user = this.userService.currentUserOrThrow();
		// Required to fetch authorities.
		user.getAuthorities();
		return this.userMapper.toSummary(user);
	}

}
