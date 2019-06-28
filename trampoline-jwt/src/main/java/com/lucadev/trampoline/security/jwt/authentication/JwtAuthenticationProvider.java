package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.jwt.JwtPayload;
import com.lucadev.trampoline.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

/**
 * A {@link AuthenticationProvider} implementation to verify and create JWT's.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 28-4-18
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final TokenService tokenService;

	private final UserDetailsService userService;

	private final PasswordEncoder passwordEncoder;

	/**
	 * Perform authentication.
	 * @param authentication the {@link Authentication} object.
	 * @return a new {@link Authentication} object based on the old one.
	 * @throws AuthenticationException when auth fails
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		log.debug("Checking authentication for JwtAuthenticationToken");
		// If there's already a JwtAuthenticationToken present
		if (authentication instanceof JwtAuthenticationToken) {
			JwtPayload payload = ((JwtAuthenticationToken) authentication)
					.getJwtPayload();
			return createValidatedJwtAuthentication(payload);
		}
		else {
			return createNewJwtAuthentication(authentication);
		}
	}

	/**
	 * Authorize when already owner of a JWT token.
	 * @param jwtPayload the JWT data.
	 * @return the new {@link Authentication} object
	 */
	private Authentication createValidatedJwtAuthentication(JwtPayload jwtPayload) {
		UserDetails user = this.userService.loadUserByUsername(jwtPayload.getUsername());
		validateToken(user, jwtPayload);
		checkAllowance(user);
		return new JwtAuthenticationToken(user.getAuthorities(), user, jwtPayload);
	}

	/**
	 * Check if the user is allowed to authorize.
	 * @param user user to check.
	 */
	private void checkAllowance(UserDetails user) {
		if (!user.isAccountNonLocked()) {
			throw new LockedException(
					"Could not authorize user because the account is locked.");
		}

		if (!user.isEnabled()) {
			throw new DisabledException(
					"Could not authorize user because the account is disabled.");
		}

		if (!user.isCredentialsNonExpired()) {
			throw new AccountExpiredException(
					"Could not authorize user because the credentials are expired.");
		}

		if (!user.isAccountNonExpired()) {
			throw new AccountExpiredException(
					"Could not authorize user because the account is expired.");
		}
	}

	/**
	 * Authenticate.
	 * @param authentication the auth object.
	 * @return a jwt auth object.
	 */
	private Authentication createNewJwtAuthentication(Authentication authentication) {
		UserDetails user = getUserDetails(authentication);
		String token = this.tokenService.issueToken(user);
		JwtPayload payload = this.tokenService.parseToken(token);
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new JwtAuthenticationToken(authorities, user, payload);
	}

	/**
	 * Validate the token against the actual user.
	 * @param userDetails user to validate.
	 * @param jwtPayload token to validate against user.
	 */
	private void validateToken(UserDetails userDetails, JwtPayload jwtPayload) {
		if (!this.tokenService.isValidToken(jwtPayload, userDetails)) {
			throw new BadCredentialsException(
					"Token did not validate against user with success.");
		}
	}

	/**
	 * Get user from authentication.
	 * @param authentication find user from auth object.
	 * @return the resolved user.
	 */
	private UserDetails getUserDetails(Authentication authentication) {
		String name = authentication.getName();
		String credentials = String.valueOf(authentication.getCredentials());
		UserDetails userDetails = this.userService.loadUserByUsername(name);

		boolean correctCredentials = this.passwordEncoder.matches(credentials,
				userDetails.getPassword());
		if (correctCredentials) {
			return userDetails;
		}
		throw new BadCredentialsException("Invalid credentials?");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return JwtAuthenticationToken.class.isAssignableFrom(aClass)
				|| UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
	}

}
