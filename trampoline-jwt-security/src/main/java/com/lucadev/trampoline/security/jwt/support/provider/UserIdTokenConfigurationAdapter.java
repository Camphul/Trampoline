package com.lucadev.trampoline.security.jwt.support.provider;

import com.lucadev.trampoline.security.authentication.PersistentUserDetails;
import com.lucadev.trampoline.security.jwt.adapter.TokenConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Adapter used to add principal id from user.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Slf4j
@RequiredArgsConstructor
public class UserIdTokenConfigurationAdapter implements TokenConfigurationAdapter {

	private final JwtSecurityConfigurationProperties jwtSecurityConfigurationProperties;

	@Override
	public boolean shouldIgnoreExpiration(UserDetails user) {
		return false;
	}

	@Override
	public void createToken(UserDetails userDetails, Map<String, Object> claims) {
		if (userDetails instanceof PersistentUserDetails) {
			log.debug("Adding principal id claim.");
			PersistentUserDetails persistentUserDetails = (PersistentUserDetails) userDetails;
			claims.put(
					jwtSecurityConfigurationProperties.getClaims()
							.getPrincipalIdentifier(),
					persistentUserDetails.getUser().getId().toString());

		}
	}

}
