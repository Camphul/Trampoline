package com.lucadev.trampoline.security.jwt.impl;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import com.lucadev.trampoline.security.jwt.JwtAuthenticationToken;
import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityProperties;
import com.lucadev.trampoline.security.jwt.model.JwtPayload;
import com.lucadev.trampoline.security.model.Role;
import com.lucadev.trampoline.security.model.User;
import com.lucadev.trampoline.security.service.UserService;
import com.lucadev.trampoline.service.time.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation for {@link TokenService} used to manage JWT tokens.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private static final String CLAIM_USERNAME = "trampolineUsername";
    private static final String CLAIM_EMAIL = "trampolineEmail";
    private static final String CLAIM_ROLES = "trampolineUserRoles";
    private static final String CLAIM_IGNORE_EXPIRATION_TIMEOUT = "tokenIgnoreExpiration";
    /**
     * Boolean value to see if the token is used as impersonate
     */
    private static final String CLAIM_IS_IMPERSONATING = "tokenImpersonateMode";
    /**
     * The UUID of the User who initiated the impersonate mode
     */
    private static final String CLAIM_IMPERSONATE_INITIATOR = "tokenImpersonateInitiator";

    private final TimeProvider timeProvider;
    private final UserService userService;
    private final JwtSecurityProperties properties;
    private final AuthenticationManager authenticationManager;

    /**
     * Create a new token
     *
     * @param user
     * @return
     */
    @Override
    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USERNAME, user.getUsername());
        claims.put(CLAIM_EMAIL, user.getEmail());
        claims.put(CLAIM_ROLES, user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        claims.put(CLAIM_IGNORE_EXPIRATION_TIMEOUT, false);
        claims.put(CLAIM_IS_IMPERSONATING, false);
        return generateToken(claims, user.getId().toString());
    }

    /**
     * Refresh an existing token without any checks
     *
     * @param token
     * @return
     */
    @Override
    public String refreshToken(String token) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        //copy over old claims
        final Claims claims = getAllTokenClaims(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.forName(properties.getSigningAlgorithm()), properties.getSecret())
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
                .signWith(SignatureAlgorithm.forName(properties.getSigningAlgorithm()), properties.getSecret())
                .compact();
    }

    private boolean isTokenRefreshable(JwtPayload token, Date lastPasswordReset) {
        return !isCreatedDateTimeBeforeLastPasswordResetDateTime(token.getIssuedDate(), lastPasswordReset)
                && (!isCurrentDateTimePastExpiryDateTime(token.getExpirationDate()) || token.isIgnorableExpiration());
    }

    /**
     * Get all token information
     *
     * @param token
     * @return
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
        jwtPayload.setImpersonateMode(claims.get(CLAIM_IS_IMPERSONATING, Boolean.class));
        if (jwtPayload.isImpersonateMode()) {
            jwtPayload.setImpersonateInitiatorId(UUID.fromString(
                    claims.get(CLAIM_IMPERSONATE_INITIATOR, String.class)));
        }
        jwtPayload.setIgnorableExpiration(claims.get(CLAIM_IGNORE_EXPIRATION_TIMEOUT, Boolean.class));
        jwtPayload.setRoles((List<String>) claims.get(CLAIM_ROLES, ArrayList.class));
        return jwtPayload;
    }

    @Override
    public JwtPayload getTokenDataFromRequest(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getTokenHeader());
        if(requestHeader == null || requestHeader.isEmpty()) {
            throw new AuthenticationException("Could not find token header.");
        }
        JwtPayload jwtPayload = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenHeaderPrefix())) {
            authToken = getTokenFromHeader(requestHeader);
            try {
                jwtPayload = getTokenData(authToken);
            } catch (IllegalArgumentException e) {
                throw new AuthenticationException("Could not parse token");
            } catch (ExpiredJwtException e) {
                throw new AuthenticationException("Token is expired");
            }
        } else {
            throw new AuthenticationException("Could not find bearer string.");
        }
        return jwtPayload;
    }

    /**
     * Validate token data against the suspected User
     *
     * @param jwtPayload
     * @param user
     * @return
     */
    @Override
    public boolean isValidToken(JwtPayload jwtPayload, User user) {
        return (
                user.getId().equals(jwtPayload.getSubject())
                        && user.getUsername().equals(jwtPayload.getUsername())
                        && (!isCurrentDateTimePastExpiryDateTime(jwtPayload.getExpirationDate()) || jwtPayload.isIgnorableExpiration())
                        && !isCreatedDateTimeBeforeLastPasswordResetDateTime(jwtPayload.getIssuedDate(), user.getLastPasswordReset())
        );
    }

    /**
     * Handle a request to refresh a token
     *
     * @param request
     * @return
     */
    @Override
    public String processTokenRefreshRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(properties.getTokenHeader());
        final String token = getTokenFromHeader(authHeader);
        JwtPayload jwtPayload = getTokenData(token);
        String username = jwtPayload.getUsername();
        User user = userService.findById(jwtPayload.getSubject());
        if (!user.getUsername().equals(username)) {
            throw new AuthenticationException("Token subject does not match user");
        }

        if (isTokenRefreshable(jwtPayload, user.getLastPasswordReset())) {
            return refreshToken(token);
        } else {
            throw new AuthenticationException("Auth token can not be refreshed");
        }
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        try {
            JwtPayload jwtPayload = getTokenDataFromRequest(request);
            if (jwtPayload == null) {
                return null;
            }
            //TODO: Jwt auth token for authentication manager
            JwtAuthenticationToken token = new JwtAuthenticationToken(jwtPayload);
            return authenticationManager.authenticate(token);
        } catch (Exception ex) {
            return null;
        }
    }

    private Claims getAllTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(properties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Is created date before the given lastPasswordReset date.
     *
     * @param created
     * @param lastPasswordReset
     * @return
     */
    private boolean isCreatedDateTimeBeforeLastPasswordResetDateTime(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * If the current date is higher than the given date
     *
     * @param expiration
     * @return
     */
    private boolean isCurrentDateTimePastExpiryDateTime(Date expiration) {
        return expiration.before(timeProvider.now());
    }

    /**
     * Calculate expiry date
     *
     * @param createdDate
     * @return
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + properties.getTokenTimeout() * 1000);
    }

    private String getTokenFromHeader(String headerValue) {
        String authToken = headerValue.substring(properties.getTokenHeaderPrefix().length());
        //Remove first whitespace
        while (authToken.startsWith(" ")) {
            authToken = authToken.substring(1);
        }
        return authToken;
    }
}