package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.jwt.TokenPayload;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Abstract token pre auth class.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
public abstract class AbstractTokenPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

	/**
	 * Obtain the token used for further authentication.
	 *
	 * @param request http request.
	 * @return token payload.
	 */
	public abstract TokenPayload getToken(HttpServletRequest request);

	/**
	 * Get principal in the form of a token.
	 *
	 * @param request http request.
	 * @return token payload.
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return getToken(request);
	}

	/**
	 * Fetch credentials from the request in the form of a token.
	 *
	 * @param request the http request.
	 * @return token.
	 */
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return getToken(request);
	}
}
