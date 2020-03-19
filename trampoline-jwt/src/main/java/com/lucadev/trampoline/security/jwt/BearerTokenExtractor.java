package com.lucadev.trampoline.security.jwt;

import com.lucadev.trampoline.security.jwt.configuration.JwtSecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements {@link TokenExtractor} and extracts bearer token.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/19/20
 */
@RequiredArgsConstructor
public class BearerTokenExtractor implements TokenExtractor {

	private final JwtSecurityConfigurationProperties properties;

	/**
	 * Get bearer token.
	 * @param request http request.
	 * @return bearer token.
	 */
	@Override
	public String extract(HttpServletRequest request) throws RuntimeException {
		return extract(request.getHeader(this.properties.getHeader()));
	}

	/**
	 * Get bearer token.
	 * @param header header value.
	 * @return bearer token.
	 */
	@Override
	public String extract(String header) throws RuntimeException {
		if (header == null || !header.startsWith(this.properties.getHeaderSchema())) {
			throw new AuthenticationCredentialsNotFoundException(
					"Could not find correct token(schema)");
		}
		return parseTokenHeader(header);
	}

	private String parseTokenHeader(String headerValue) {
		String authToken = headerValue
				.substring(this.properties.getHeaderSchema().length());
		// Remove first whitespace
		while (authToken.startsWith(" ")) {
			authToken = authToken.substring(1);
		}
		return authToken;
	}

}
