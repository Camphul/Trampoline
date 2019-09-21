package com.lucadev.trampoline.security.jwt.support.provider;

import com.lucadev.trampoline.security.jwt.TokenPayload;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.authentication.StatelessAuthenticationToken;
import com.lucadev.trampoline.security.jwt.authentication.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Authentication provider which creates an authentication object from a
 * username password login.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Slf4j
@RequiredArgsConstructor
public class UserDetailsTokenAuthenticationProvider
		implements TokenAuthenticationProvider {

	private final TokenService tokenService;

	private final UserDetailsService userDetailsService;

	private final UserDetailsChecker userDetailsChecker;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		log.debug("Issuing new token.");
		UsernamePasswordAuthenticationToken authorizationToken = (UsernamePasswordAuthenticationToken) authentication;
		UserDetails userDetails = this.userDetailsService
				.loadUserByUsername(authorizationToken.getName());
		// Checks attributes such as enabled/credentials expired/etc..
		this.userDetailsChecker.check(userDetails);
		String token = this.tokenService.issueToken(userDetails);
		TokenPayload tokenPayload = this.tokenService.decodeToken(token);
		return new StatelessAuthenticationToken(userDetails, tokenPayload);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
