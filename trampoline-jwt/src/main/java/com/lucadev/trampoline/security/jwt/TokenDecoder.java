package com.lucadev.trampoline.security.jwt;

/**
 * Interface defining a method to decode token string.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public interface TokenDecoder {

	/**
	 * Decodes token into payload.
	 *
	 * @param token string token.
	 * @return decoded token.
	 * @throws RuntimeException when we fail to decode the token.
	 */
	TokenPayload decode(String token) throws RuntimeException;

}
