package com.lucadev.trampoline.security.jwt;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface defining method to extract token from request.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
public interface TokenExtractor {

	/**
	 * Extract token header.
	 *
	 * @param request http request.
	 * @return token as string.
	 * @throws Exception when fails to extract.
	 */
	String extract(HttpServletRequest request) throws RuntimeException;

	/**
	 * Extract token header.
	 *
	 * @param header header value.
	 * @return token as string.
	 * @throws Exception when fails to extract.
	 */
	String extract(String header) throws RuntimeException;

}
