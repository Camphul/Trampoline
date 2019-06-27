package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.adapter.JwtConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.authentication.JwtAuthenticationToken;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
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

	private static final String CLAIM_USERNAME = "t_username";

	private static final String CLAIM_ROLES = "t_roles";

	private static final String CLAIM_IGNORE_EXPIRATION_TIMEOUT = "t_ignore_timout";

	private final JwtConfigurationAdapter jwtConfiguration;

	private final TimeProvider timeProvider;

	private final UserDetailsService userService;

	private final JwtSecurityConfigurationProperties properties;

	@Getter(AccessLevel.PRIVATE)
	private Key signKey;

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
		claims.put(CLAIM_USERNAME, user.getUsername());
		claims.put(CLAIM_ROLES, user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		claims.put(CLAIM_IGNORE_EXPIRATION_TIMEOUT,
				this.jwtConfiguration.shouldIgnoreExpiration(user));
		this.jwtConfiguration.createToken(user, claims);
		return generateToken(claims, user.getUsername());
	}

	/**
	 * Refresh an existing token without any checks.
	 * @param token jwt
	 * @return refreshed jwt
	 */
	@Override
	public String issueTokenRefresh(String token) {
		final Date createdDate = this.timeProvider.now();
		// copy over old claims
		final Claims claims = getAllTokenClaims(token);

		return Jwts.builder().setClaims(claims).setIssuedAt(createdDate)
				.setExpiration(getExpiryDate(createdDate))
				.signWith(getSignKey()).compact();
	}

	private String generateToken(Map<String, Object> claims, String subject) {
		final Date createdDate = this.timeProvider.now();
		final Date expirationDate = getExpiryDate(createdDate);

		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(createdDate).setExpiration(expirationDate)
				.signWith(getSignKey()).compact();
	}

	private boolean isTokenRefreshable(JwtPayload token) {
		return (!isPastExpiryDate(token.getExpirationDate())
				|| token.isIgnorableExpiration());
	}

	/**
	 * Get all token information.
	 * @param token jwt string
	 * @return jwt DTO representation.
	 */
	@Override
	public JwtPayload parseToken(String token) {
		final Claims claims = getAllTokenClaims(token);
		JwtPayload jwtPayload = new JwtPayload();
		jwtPayload.setRawToken(token);
		jwtPayload.setUsername(claims.get(CLAIM_USERNAME, String.class));
		jwtPayload.setIssuedDate(claims.getIssuedAt());
		jwtPayload.setExpirationDate(claims.getExpiration());
		jwtPayload.setIgnorableExpiration(
				claims.get(CLAIM_IGNORE_EXPIRATION_TIMEOUT, Boolean.class));
		jwtPayload.setRoles(claims.get(CLAIM_ROLES, ArrayList.class));
		return jwtPayload;
	}

	@Override
	public JwtPayload parseToken(HttpServletRequest request) {
		final String requestHeader = request
				.getHeader(JwtSecurityConfigurationProperties.TOKEN_HEADER);
		if (requestHeader == null || requestHeader.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException(
					"Could not find token header.");
		}
		if (requestHeader.startsWith(JwtSecurityConfigurationProperties.HEADER_PREFIX)) {
			String authToken = parseTokenHeader(requestHeader);
			if (authToken == null) {
				throw new AuthenticationCredentialsNotFoundException(
						"Auth token is null.");
			}
			try {
				return parseToken(authToken);
			}
			catch (IllegalArgumentException e) {
				throw new BadCredentialsException("Could not parse token");
			}
			catch (ExpiredJwtException e) {
				throw new BadCredentialsException("Token is expired");
			}
		}
		else {
			throw new AuthenticationCredentialsNotFoundException(
					"Could not find bearer string.");
		}
	}

	/**
	 * Validate token data against the suspected User.
	 * @param jwtPayload jwt dto.
	 * @param user user who the token belongs to.
	 * @return if the token is valid.
	 */
	@Override
	public boolean isValidToken(JwtPayload jwtPayload, UserDetails user) {
		return (user.getUsername().equals(jwtPayload.getUsername())
				&& (!isPastExpiryDate(jwtPayload.getExpirationDate())
						|| jwtPayload.isIgnorableExpiration()));
	}

	/**
	 * Handle a request to refresh a token.
	 * @param request http req
	 * @return jwt token string.
	 */
	@Override
	public String issueTokenRefresh(HttpServletRequest request) {
		String authHeader = request
				.getHeader(JwtSecurityConfigurationProperties.TOKEN_HEADER);
		final String token = parseTokenHeader(authHeader);
		JwtPayload jwtPayload = parseToken(token);
		String username = jwtPayload.getUsername();
		UserDetails user = userService.loadUserByUsername(username);
		if (!user.getUsername().equals(username)) {
			throw new BadCredentialsException("Token subject does not match user");
		}

		if (isTokenRefreshable(jwtPayload)) {
			return issueTokenRefresh(token);
		}
		else {
			throw new BadCredentialsException("Auth token can not be refreshed");
		}
	}

	/**
	 * Read the header containing our token and create an {@link Authentication} object
	 * from it.
	 * @param request http req
	 * @return auth object.
	 */
	@Override
	public Optional<Authentication> getAuthenticationToken(HttpServletRequest request) {
		try {
			JwtPayload jwtPayload = parseToken(request);
			if (jwtPayload == null) {
				return Optional.empty();
			}
			return Optional.of(new JwtAuthenticationToken(jwtPayload));
		}
		catch (Exception ex) {
			log.error("Failed to obtain JWT authentication object.", ex);
			return Optional.empty();
		}
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
	private boolean isPastExpiryDate(Date expiration) {
		return expiration.before(this.timeProvider.now());
	}

	/**
	 * Calculate expiry date based on the configured token timeout.
	 * @param createdDate jwt creation date.
	 * @return expiration date.
	 */
	private Date getExpiryDate(Date createdDate) {
		return new Date(createdDate.getTime() + this.properties.getTokenTimeout() * 1000);
	}

	/**
	 * Get the raw token string from the header value(remove the prefix).
	 * @param headerValue the raw header value
	 * @return the un-prefixed, ready to parse jwt token
	 */
	private String parseTokenHeader(String headerValue) {
		String authToken = headerValue
				.substring(JwtSecurityConfigurationProperties.HEADER_PREFIX.length());
		// Remove first whitespace
		while (authToken.startsWith(" ")) {
			authToken = authToken.substring(1);
		}
		return authToken;
	}

}
