package com.lucadev.trampoline.security.jwt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface defining authentication controller methods.
 *
 * @param <Response> type which is returned as auth response.
 * @param <Request> model which is used as auth request.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/2/19
 */
public interface AuthenticationController<Response, Request> {

	/**
	 * Handle an authentication request.
	 * @param authenticationRequest the authentication request coming in from the client.
	 * @return the authentication response.
	 */
	Response authenticate(Request authenticationRequest);

	/**
	 * Handle token refresh request.
	 * @param request the http request.
	 * @param response the http response.
	 * @return the refreshed token response.
	 */
	Response refresh(HttpServletRequest request, HttpServletResponse response);
}
