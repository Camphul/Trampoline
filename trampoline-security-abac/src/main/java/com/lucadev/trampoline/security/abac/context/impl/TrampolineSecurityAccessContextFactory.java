package com.lucadev.trampoline.security.abac.context.impl;

import com.lucadev.trampoline.security.abac.context.SecurityAccessContext;
import com.lucadev.trampoline.security.abac.context.SecurityAccessContextFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Default {@link SecurityAccessContextFactory} implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
public class TrampolineSecurityAccessContextFactory implements SecurityAccessContextFactory {

	@Override
	public SecurityAccessContext create(Object subject, Object resource, Object action, Object environment) {
		Authentication authentication = getAuthenticationContext();
		return new TrampolineSecurityAccessContext(authentication, subject, resource, action, environment);
	}


	/**
	 * Security context
	 *
	 * @return {@link Authentication}
	 */
	protected Authentication getAuthenticationContext() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
