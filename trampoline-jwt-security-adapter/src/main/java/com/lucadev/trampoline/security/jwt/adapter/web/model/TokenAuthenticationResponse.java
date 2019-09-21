package com.lucadev.trampoline.security.jwt.adapter.web.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Response after sending an authentication request. Contains the jwt token if the
 * authentication attempt succeeded
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 14-4-18
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TokenAuthenticationResponse {

	private final String token;

}
