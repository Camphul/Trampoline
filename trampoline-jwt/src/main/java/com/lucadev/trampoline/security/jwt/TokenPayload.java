package com.lucadev.trampoline.security.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Representation of the data inside the JWT.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Data
public class TokenPayload implements Serializable {

	/**
	 * Raw token data.
	 */
	private String rawToken;

	/**
	 * Principal id(most likely entity id)
	 */
	private String principalIdentifier;

	/**
	 * Subject username.
	 */
	private String username;

	/**
	 * Subject authorities.
	 */
	private Collection<GrantedAuthority> authorities;

	/**
	 * Token issue date.
	 */
	private Date issuedDate;

	/**
	 * Token expiry date.
	 */
	private Date expirationDate;

	/**
	 * If we can ignore expiry date.
	 */
	private boolean ignorableExpiration;

}
