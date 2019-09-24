package com.lucadev.trampoline.security.jwt.decorator;

import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Token decorator which adds the username of the user to the claims.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9/21/19
 */
@RequiredArgsConstructor
public class UsernameTokenDecorator implements TokenDecorator {

	private final JwtSecurityConfigurationProperties properties;

	@Override
	public void createClaims(UserDetails user, Map<String, Object> claims) {
		JwtSecurityConfigurationProperties.ClaimsConfigurationProperties claimConfig = this.properties
				.getClaims();
		claims.put(claimConfig.getUsername(), user.getUsername());
	}

}
