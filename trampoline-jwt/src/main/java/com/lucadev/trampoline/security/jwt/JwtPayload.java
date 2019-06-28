package com.lucadev.trampoline.security.jwt;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Representation of the data inside the JWT.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Data
public class JwtPayload implements Serializable {

	/**
	 * Raw token data.
	 */
	private String rawToken;

	/**
	 * Subject username.
	 */
	private String username;

	/**
	 * Subject authorities.
	 */
	private List<String> authorities;

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