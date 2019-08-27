package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.adapter.TokenConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import com.lucadev.trampoline.service.time.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation for {@link TokenService} used to manage JWT tokens.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

	private final TokenConfigurationAdapter tokenConfigurationAdapter;

	private final TimeProvider timeProvider;

	private final JwtSecurityConfigurationProperties properties;

	@Getter(AccessLevel.PRIVATE)
	private Key signKey;

	/**
	 * Configures the signing key after constructing the bean.
	 */
	@PostConstruct
	public void postConstruct() {
		byte[] encodedSecret = Base64.getEncoder()
				.encode(this.properties.getSecret().getBytes());
		this.signKey = Keys.hmacShaKeyFor(encodedSecret);
	}

	/**
	 * Create a new token.
	 * @param user user to create token for.
	 * @return jwt token.
	 */
	@Override
	public String issueToken(UserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		JwtSecurityConfigurationProperties.ClaimsConfigurationProperties claimConfig = this.properties
				.getClaims();
		claims.put(claimConfig.getUsername(), user.getUsername());
		claims.put(claimConfig.getAuthorities(), user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		claims.put(claimConfig.getIgnoreExpiration(),
				this.tokenConfigurationAdapter.shouldIgnoreExpiration(user));
		this.tokenConfigurationAdapter.createToken(user, claims);
		return generateToken(claims, user.getUsername());
	}

	/**
	 * Refresh an existing token without any checks.
	 * @param token jwt
	 * @return refreshed jwt
	 */
	@Override
	public String issueTokenRefresh(String token) {
		// Since JJWT does not support LocalDateTime/Instant
		final Date createdDate = new Date(this.timeProvider.unix());
		// copy over old claims
		final Claims claims = getAllTokenClaims(token);

		return Jwts.builder().setClaims(claims).setIssuedAt(createdDate)
				.setExpiration(calculateExpiryDate(createdDate)).signWith(getSignKey())
				.compact();
	}

	/**
	 * Handle a request to refresh a token.
	 * @param request http req
	 * @return jwt token string.
	 */
	@Override
	public String issueTokenRefresh(HttpServletRequest request) {
		String authHeader = request.getHeader(this.properties.getHeader());
		final String token = parseTokenHeader(authHeader);
		TokenPayload tokenPayload = decodeToken(token);

		if (isTokenRefreshable(tokenPayload)) {
			return issueTokenRefresh(token);
		}
		else {
			throw new BadCredentialsException("Auth token can not be refreshed");
		}
	}

	private String generateToken(Map<String, Object> claims, String subject) {
		// Since JJWT does not support java 8 time api yet.
		final Date createdDate = new Date(this.timeProvider.unix());
		final Date expirationDate = calculateExpiryDate(createdDate);

		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(createdDate).setExpiration(expirationDate)
				.signWith(getSignKey()).compact();
	}

	private boolean isTokenRefreshable(TokenPayload token) {
		return (!isExpired(token.getExpirationDate()) || token.isIgnorableExpiration());
	}

	/**
	 * Get all token information.
	 * @param token jwt string
	 * @return jwt DTO representation.
	 */
	@Override
	public TokenPayload decodeTokenHeader(String token) {
		if (token == null || token.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Could not find token.");
		}
		if (token.startsWith(this.properties.getHeaderSchema())) {
			String authToken = parseTokenHeader(token);
			if (authToken == null) {
				throw new AuthenticationCredentialsNotFoundException(
						"Authentication token was not found inside header.");
			}
			return decodeToken(authToken);
		} else {
			throw new AuthenticationCredentialsNotFoundException(
					"Could not find bearer string inside header.");
		}
	}

	@Override
	public TokenPayload decodeToken(String token) {
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
			tokenPayload.setIgnorableExpiration(
					claims.get(claimConfig.getIgnoreExpiration(), Boolean.class));
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
	 * Validate token data against the suspected User.
	 * @param tokenPayload jwt dto.
	 * @param user user who the token belongs to.
	 * @return if the token is valid.
	 */
	@Override
	public boolean isValidToken(TokenPayload tokenPayload, UserDetails user) {
		return (user.getUsername().equals(tokenPayload.getUsername())
				&& (!isExpired(tokenPayload.getExpirationDate())
				|| tokenPayload.isIgnorableExpiration()));
	}

	@Override
	public String getTokenHeader(HttpServletRequest request) {
		return request.getHeader(this.properties.getHeader());
	}

	/**
	 * Parse JWT and obtain all claims.
	 * @param token jwt string.
	 * @return all token claims.
	 * @see Claims
	 */
	private Claims getAllTokenClaims(String token) {
		return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
	}

	/**
	 * If the current date is higher than the given date.
	 * @param expiration expiration date.
	 * @return if current datetime is before expiration.
	 */
	private boolean isExpired(Date expiration) {
		// Since JJWT does not support Java 8 time API for now.
		Date current = new Date(this.timeProvider.unix());
		return expiration.before(current);
	}

	/**
	 * Calculate expiry date based on the configured token timeout. Does not use Java 8
	 * time API since JJWT does not support it yet.
	 * @param createdDate jwt creation date.
	 * @return expiration date.
	 */
	private Date calculateExpiryDate(Date createdDate) {
		long timeoutMilliseconds = this.properties.getTokenTimeout() * 1000L;
		return new Date(createdDate.getTime() + timeoutMilliseconds);
	}

	/**
	 * Get the raw token string from the header value(remove the prefix).
	 * @param headerValue the raw header value
	 * @return the un-prefixed, ready to parse jwt token
	 */
	private String parseTokenHeader(String headerValue) {
		String authToken = headerValue
				.substring(this.properties.getHeaderSchema().length());
		// Remove first whitespace
		while (authToken.startsWith(" ")) {
			authToken = authToken.substring(1);
		}
		return authToken;
	}

}
