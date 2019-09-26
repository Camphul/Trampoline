package com.lucadev.trampoline.security.abac.decorator;

import java.util.Map;

/**
 * Decorator for policy environments to use while evaluating.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9/26/19
 */
public interface PolicyEnvironmentDecorator {

	/**
	 * Decorate the environment variables.
	 *
	 * @param environment environment variables.
	 */
	void decorate(Map<String, Object> environment);

}
