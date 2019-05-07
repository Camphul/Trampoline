package com.lucadev.trampoline.security.abac.spel.context;

/**
 * Interface that defines a method to create a {@link SecurityAccessContext}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
public interface SecurityAccessContextFactory {

	/**
	 * Create the {@link SecurityAccessContext}
	 *
	 * @param subject     the subject executing an action
	 * @param resource    the resource being accessed by the subject
	 * @param action      the action being taken by the subject against the resource
	 * @param environment the environment/context in which the action is taken.
	 * @return a {@link SecurityAccessContext}
	 */
	SecurityAccessContext create(Object subject, Object resource, Object action, Object environment);

}
