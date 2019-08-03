package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.jwt.TokenPayload;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Authentication token for JWT.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 28-4-18
 */
@ToString
@Getter
@EqualsAndHashCode
public class StatelessAuthenticationToken extends AbstractAuthenticationToken {

	private final TokenPayload tokenPayload;

	private final Object principal;

	/**
	 * Construct the token with raw jwt data. Not setting authenticated.
	 * @param tokenPayload the JWT representation.
	 */
	public StatelessAuthenticationToken(TokenPayload tokenPayload) {
		super(tokenPayload.getAuthorities());
		this.tokenPayload = tokenPayload;
		this.principal = tokenPayload.getUsername();
	}

	/**
	 * Construct an authenticated token.
	 * @param user the actual user.
	 * @param tokenPayload the jwt linked to the user.
	 */
	public StatelessAuthenticationToken(UserDetails user, TokenPayload tokenPayload) {
		super(user.getAuthorities());
		this.tokenPayload = tokenPayload;
		this.principal = user;
		setAuthenticated(true);
	}

	/**
	 * Authentication credentials.
	 * @return authorities.
	 */
	@Override
	public Object getCredentials() {
		return this.tokenPayload.getAuthorities();
	}

	/**
	 * {@inheritDoc}
	 * @return either username or {@link UserDetails} object when authenticated.
	 */
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	/**
	 * Username inside the jwt token.
	 * @return JWT username.
	 */
	@Override
	public String getName() {
		return this.tokenPayload.getUsername();
	}

	/**
	 * Jwt payload when authenticated. Else {@code super.getDetails();}
	 * @return jwt payload data.
	 */
	@Override
	public Object getDetails() {
		return isAuthenticated() ? this.tokenPayload.getRawToken() : super.getDetails();
	}

}
