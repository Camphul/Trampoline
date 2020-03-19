package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT decoder.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@RequiredArgsConstructor
public class JwtTokenDecoder implements TokenDecoder {

	private final JwtSecurityConfigurationProperties properties;

	/**
	 * Decodes jwt token.
	 *
	 * @param token string token.
	 * @return decoded token payload.
	 * @throws Exception when we fail to decode due to invalid token.
	 */
	@Override
	public TokenPayload decode(String token) throws RuntimeException {
		try {
			JwtSecurityConfigurationProperties.ClaimsConfigurationProperties claimConfig = this.properties
					.getClaims();
			Claims claims = getAllTokenClaims(token);
			TokenPayload tokenPayload = new TokenPayload();
			tokenPayload.setRawToken(token);
			tokenPayload.setUsername(claims.get(claimConfig.getUsername(), String.class));
			tokenPayload.setIssuedDate(claims.getIssuedAt());
			tokenPayload.setExpirationDate(claims.getExpiration());
			tokenPayload.setPrincipalIdentifier(
					claims.get(claimConfig.getPrincipalIdentifier(), String.class));
			Collection<GrantedAuthority> authorities = ((List<String>) claims
					.get(claimConfig.getAuthorities(), ArrayList.class)).stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			tokenPayload.setAuthorities(authorities);
			return tokenPayload;
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to parse token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			throw new BadCredentialsException("Token is expired.");
		}
	}

	/**
	 * Get jwt sign key.
	 *
	 * @return jwt sign key.
	 */
	private Key getSignKey() {
		byte[] encodedSecret = Base64.getEncoder()
				.encode(this.properties.getSecret().getBytes());
		return Keys.hmacShaKeyFor(encodedSecret);
	}

	/**
	 * Parse JWT and obtain all claims.
	 *
	 * @param token jwt string.
	 * @return all token claims.
	 * @see Claims
	 */
	private Claims getAllTokenClaims(String token) {
		return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
	}

}
