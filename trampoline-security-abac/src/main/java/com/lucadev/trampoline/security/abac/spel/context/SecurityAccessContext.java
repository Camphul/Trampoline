package com.lucadev.trampoline.security.abac.spel.context;

import org.springframework.security.access.expression.SecurityExpressionOperations;

/**
 * Contains the context required to evaluate access.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 */
public interface SecurityAccessContext extends SecurityExpressionOperations {

	/**
	 * Identity who invoked an action(user).
	 * @return Identity trying to access the resource
	 */
	Object getSubject();

	/**
	 * Resource being acted upon.
	 * @return the resource being accessed by the subject
	 */
	Object getResource();

	/**
	 * Action being ran on the resource.
	 * @return the action being taken against the resource by the subject
	 */
	Object getAction();

	/**
	 * Environment where the action is taking place.
	 * @return the environment/context in which the action is being taken against the
	 * resource by the subject.
	 */
	Object getEnvironment();

	/**
	 * Check if the given action is equal to the action in the parameters.
	 * @param action the action to check
	 * @return if the actions match
	 */
	boolean isAction(Object action);

	/**
	 * If the given argument is the current subject.
	 * @param user the user object to test.
	 * @return if the subject is the given user.
	 */
	boolean isSubject(Object user);

}
