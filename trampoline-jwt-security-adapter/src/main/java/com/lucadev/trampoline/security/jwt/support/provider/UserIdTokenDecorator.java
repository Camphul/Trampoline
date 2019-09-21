package com.lucadev.trampoline.security.jwt.support.provider;

import com.lucadev.trampoline.security.authentication.PersistentUserDetails;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.security.jwt.decorator.TokenDecorator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Token decorator which adds the principal's id to the token(user id).
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Slf4j
@RequiredArgsConstructor
public class UserIdTokenDecorator implements TokenDecorator {

	private final JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties;

	@Override
	public void createClaims(UserDetails userDetails, Map<String, Object> claims) {
		if (userDetails instanceof PersistentUserDetails) {
			log.debug("Adding principal id claim.");
			PersistentUserDetails persistentUserDetails = (PersistentUserDetails) userDetails;
			claims.put(
					this.jwtSecurityConfigurationProperties.getClaims()
							.getPrincipalIdentifier(),
					persistentUserDetails.getUser().getId().toString());

		}
	}

}
