package com.lucadev.trampoline.security.jwt.authentication.user;

import com.lucadev.trampoline.security.jwt.TokenDecoder;
import com.lucadev.trampoline.security.jwt.TokenExtractor;
import com.lucadev.trampoline.security.jwt.TokenPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * User details service which obtains the userdetails from an authentication object. This
 * authentication object copntains our token from which we can create a token userdetails.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Slf4j
@RequiredArgsConstructor
public class StatelessUserDetailsService
		implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	private final TokenDecoder tokenDecoder;

	private final TokenExtractor tokenExtractor;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authentication)
			throws UsernameNotFoundException {
		if (authentication.getPrincipal() != null
				&& authentication.getPrincipal() instanceof String
				&& authentication.getCredentials() instanceof String) {
			log.debug("Loading stateless user details.");
			try {
				String token = this.tokenExtractor
						.extract((String) authentication.getPrincipal());
				TokenPayload tokenPayload = this.tokenDecoder.decode(token);
				return new StatelessUserDetails(tokenPayload);
			}
			catch (Exception ex) {
				throw new UsernameNotFoundException(
						"Could not decode token: " + ex.getMessage());
			}
		}
		else {
			throw new UsernameNotFoundException("Could not find user details for '"
					+ authentication.getPrincipal() + "'");
		}
	}

}
