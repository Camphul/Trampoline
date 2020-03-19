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

	private String rawToken;

	private String principalIdentifier;

	private String username;

	private Collection<GrantedAuthority> authorities;

	private Date issuedDate;

	private Date expirationDate;

}
