package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.authentication.JwtAuthenticationToken;
import com.lucadev.trampoline.security.jwt.configuration.JwtConfigurationAdapter;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.persistence.entity.Role;
import com.lucadev.trampoline.security.persistence.entity.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

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
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenService.class);

	private static final String CLAIM_USERNAME = "t_username";
	private static final String CLAIM_EMAIL = "t_mail";
	private static final String CLAIM_ROLES = "t_roles";
	private static final String CLAIM_IGNORE_EXPIRATION_TIMEOUT = "t_ignore_timout";

	private final JwtConfigurationAdapter jwtConfiguration;
	private final TimeProvider timeProvider;
	private final UserService userService;
	private final JwtSecurityProperties properties;
	@Getter(AccessLevel.PRIVATE)
	private Key signKey;

	@PostConstruct
	public void postConstruct() {
		byte[] encodedSecret = Base64.getEncoder().encode(this.properties.getSecret().getBytes());
		this.signKey = Keys.hmacShaKeyFor(encodedSecret);
	}

	/**
	 * Create a new token
	 *
	 * @param user user to create token for.
	 * @return jwt token.
	 */
	@Override
	public String createToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_USERNAME, user.getUsername());
		claims.put(CLAIM_EMAIL, user.getEmail());
		claims.put(CLAIM_ROLES, user.getRoles().stream()
				.map(Role::getName)
				.collect(Collectors.toList()));
		claims.put(CLAIM_IGNORE_EXPIRATION_TIMEOUT, jwtConfiguration.shouldIgnoreExpiration(user));
		jwtConfiguration.createToken(user, claims);
		return generateToken(claims, user.getId().toString());
	}

	/**
	 * Refresh an existing token without any checks
	 *
	 * @param token jwt
	 * @return refreshed jwt
	 */
	@Override
	public String refreshToken(String token) {
		final Date createdDate = timeProvider.now();
		//copy over old claims
		final Claims claims = getAllTokenClaims(token);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(createdDate)
				.setExpiration(calculateExpirationDate(createdDate))
				.signWith(getSignKey())
				.compact();
	}

	private String generateToken(Map<String, Object> claims, String subject) {
		final Date createdDate = timeProvider.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(getSignKey())
				.compact();
	}

	private boolean isTokenRefreshable(JwtPayload token, Date lastPasswordReset) {
		return !isCreatedDateTimeBeforeLastPasswordResetDateTime(token.getIssuedDate(), lastPasswordReset)
				&& (!isDateExpired(token.getExpirationDate()) || token.isIgnorableExpiration());
	}

	/**
	 * Get all token information
	 *
	 * @param token jwt string
	 * @return jwt DTO representation.
	 */
	@Override
	public JwtPayload getTokenData(String token) {
		final Claims claims = getAllTokenClaims(token);
		JwtPayload jwtPayload = new JwtPayload();
		jwtPayload.setRawToken(token);
		jwtPayload.setSubject(UUID.fromString(claims.getSubject()));
		jwtPayload.setUsername(claims.get(CLAIM_USERNAME, String.class));
		jwtPayload.setEmail(claims.get(CLAIM_EMAIL, String.class));
		jwtPayload.setIssuedDate(claims.getIssuedAt());
		jwtPayload.setExpirationDate(claims.getExpiration());
		jwtPayload.setIgnorableExpiration(claims.get(CLAIM_IGNORE_EXPIRATION_TIMEOUT, Boolean.class));
		jwtPayload.setRoles((List<String>) claims.get(CLAIM_ROLES, ArrayList.class));
		return jwtPayload;
	}

	@Override
	public JwtPayload getTokenData(HttpServletRequest request) {
		final String requestHeader = request.getHeader(JwtSecurityProperties.TOKEN_HEADER);
		if (requestHeader == null || requestHeader.isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Could not find token header.");
		}
		if (requestHeader.startsWith(JwtSecurityProperties.HEADER_PREFIX)) {
			String authToken = getTokenFromHeader(requestHeader);
			if (authToken == null) {
				throw new AuthenticationCredentialsNotFoundException("Auth token is null.");
			}
			try {
				return getTokenData(authToken);
			} catch (IllegalArgumentException e) {
				throw new BadCredentialsException("Could not parse token");
			} catch (ExpiredJwtException e) {
				throw new BadCredentialsException("Token is expired");
			}
		} else {
			throw new AuthenticationCredentialsNotFoundException("Could not find bearer string.");
		}
	}

	/**
	 * Validate token data against the suspected User
	 *
	 * @param jwtPayload jwt dto.
	 * @param user       user who the token belongs to.
	 * @return if the token is valid.
	 */
	@Override
	public boolean isValidToken(JwtPayload jwtPayload, User user) {
		return (
				user.getId().equals(jwtPayload.getSubject())
						&& user.getUsername().equals(jwtPayload.getUsername())
						&& (!isDateExpired(jwtPayload.getExpirationDate()) || jwtPayload.isIgnorableExpiration())
						&& !isCreatedDateTimeBeforeLastPasswordResetDateTime(jwtPayload.getIssuedDate(), user.getLastPasswordReset())
		);
	}

	/**
	 * Handle a request to refresh a token
	 *
	 * @param request http req
	 * @return jwt token string.
	 */
	@Override
	public String refreshTokenFromRequest(HttpServletRequest request) {
		String authHeader = request.getHeader(JwtSecurityProperties.TOKEN_HEADER);
		final String token = getTokenFromHeader(authHeader);
		JwtPayload jwtPayload = getTokenData(token);
		String username = jwtPayload.getUsername();
		User user = userService.findById(jwtPayload.getSubject());
		if (!user.getUsername().equals(username)) {
			throw new BadCredentialsException("Token subject does not match user");
		}

		if (isTokenRefreshable(jwtPayload, user.getLastPasswordReset())) {
			return refreshToken(token);
		} else {
			throw new BadCredentialsException("Auth token can not be refreshed");
		}
	}

	/**
	 * Read the header containing our token and create an {@link Authentication} object from it.
	 *
	 * @param request http req
	 * @return auth object.
	 */
	@Override
	public Optional<Authentication> getAuthenticationToken(HttpServletRequest request) {
		try {
			JwtPayload jwtPayload = getTokenData(request);
			if (jwtPayload == null) {
				return Optional.empty();
			}
			return Optional.of(new JwtAuthenticationToken(jwtPayload));
		} catch (Exception ex) {
			LOGGER.error("Failed to obtain JWT authentication object.", ex);
			return Optional.empty();
		}
	}

	private Claims getAllTokenClaims(String token) {
		return Jwts.parser()
				.setSigningKey(getSignKey())
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * Is created date before the given lastPasswordReset date.
	 *
	 * @param created           date created
	 * @param lastPasswordReset last password reset date
	 * @return if the token dates are valid.
	 */
	private boolean isCreatedDateTimeBeforeLastPasswordResetDateTime(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	/**
	 * If the current date is higher than the given date
	 *
	 * @param expiration expiration date.
	 * @return if current datetime is before expiration.
	 */
	private boolean isDateExpired(Date expiration) {
		return expiration.before(timeProvider.now());
	}

	/**
	 * Calculate expiry date based on the configured token timeout
	 *
	 * @param createdDate jwt creation date.
	 * @return expiration date.
	 */
	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + properties.getTokenTimeout() * 1000);
	}

	/**
	 * Get the raw token string from the header value(remove the prefix)
	 *
	 * @param headerValue the raw header value
	 * @return the un-prefixed, ready to parse jwt token
	 */
	private String getTokenFromHeader(String headerValue) {
		String authToken = headerValue.substring(JwtSecurityProperties.HEADER_PREFIX.length());
		//Remove first whitespace
		while (authToken.startsWith(" ")) {
			authToken = authToken.substring(1);
		}
		return authToken;
	}
}