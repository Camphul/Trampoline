package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.jwt.JwtPayload;
import com.lucadev.trampoline.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
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

	private final UserDetailsChecker userDetailsChecker;

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
			return authorizeToken(payload);
		}
		else {
			return authenticateTokenRequest(authentication);
		}
	}

	/**
	 * Authenticate existing token in the form of a JWT payload and issue a jwt
	 * authentication.
	 * @param jwtPayload the JWT data.
	 * @return the new {@link Authentication} object
	 */
	private Authentication authorizeToken(JwtPayload jwtPayload) {
		UserDetails user = this.userService.loadUserByUsername(jwtPayload.getUsername());
		validateToken(user, jwtPayload);
		this.userDetailsChecker.check(user);
		return new JwtAuthenticationToken(user.getAuthorities(), user, jwtPayload);
	}

	/**
	 * Authorize authentication and then issue a new JWT.
	 * @param authentication the auth object.
	 * @return a jwt auth object.
	 */
	private Authentication authenticateTokenRequest(Authentication authentication) {
		UserDetails user = getUserDetails(authentication);
		this.userDetailsChecker.check(user);
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
