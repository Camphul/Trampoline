package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.jwt.JwtPayload;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Authentication token for JWT.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 28-4-18
 */
@ToString
@Getter
@EqualsAndHashCode
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final JwtPayload jwtPayload;

	private final Object principal;

	/**
	 * Construct the token with raw jwt data. Not setting authenticated.
	 * @param jwtPayload the JWT representation.
	 */
	public JwtAuthenticationToken(JwtPayload jwtPayload) {
		super(jwtPayload.getAuthorities());
		this.jwtPayload = jwtPayload;
		this.principal = jwtPayload.getUsername();
	}

	/**
	 * Construct an authenticated token.
	 * @param authorities the user authorities.
	 * @param user the actual user.
	 * @param jwtPayload the jwt linked to the user.
	 */
	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
			UserDetails user, JwtPayload jwtPayload) {
		super(authorities);
		this.jwtPayload = jwtPayload;
		this.principal = user;
		setAuthenticated(true);
	}

	/**
	 * Authentication credentials.
	 * @return authorities.
	 */
	@Override
	public Object getCredentials() {
		return this.jwtPayload.getAuthorities();
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
		return this.jwtPayload.getUsername();
	}

	/**
	 * Jwt payload when authenticated. Else {@code super.getDetails();}
	 * @return jwt payload data.
	 */
	@Override
	public Object getDetails() {
		return isAuthenticated() ? this.jwtPayload.getRawToken() : super.getDetails();
	}

	/**
	 * Get the user.
	 * @return null if the principal is not a {@link UserDetails}
	 */
	public UserDetails getUserDetails() {
		if (!(this.principal instanceof UserDetails)) {
			return null;
		}
		return (UserDetails) this.principal;
	}

}
