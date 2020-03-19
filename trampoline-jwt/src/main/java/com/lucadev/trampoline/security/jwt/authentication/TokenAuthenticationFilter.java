package com.lucadev.trampoline.security.jwt.authentication;

import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * Pre authenticate by obtaining the token payload from the request.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter
		extends AbstractTokenPreAuthenticatedProcessingFilter {

	private final JwtSecurityConfigurationProperties properties;

	@Override
	public String getTokenHeader(HttpServletRequest request) {
		return request.getHeader(this.properties.getHeader());
	}

}
