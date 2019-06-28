package com.lucadev.trampoline.security.abac.spel.context.impl;

import com.lucadev.trampoline.security.abac.spel.context.SecurityAccessContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Handle method security methods.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 20-5-18
 * @see SecurityAccessContext
 */
@Getter
@Setter
@EqualsAndHashCode
public class TrampolineSecurityAccessContext extends SecurityExpressionRoot
		implements SecurityAccessContext {

	private Object subject;

	private Object resource;

	private Object action;

	private Object environment;

	protected TrampolineSecurityAccessContext(Authentication authentication) {
		super(authentication);
	}

	public TrampolineSecurityAccessContext(Authentication authentication, Object subject,
			Object resource, Object action, Object environment) {
		this(authentication);
		this.subject = subject;
		this.resource = resource;
		this.action = action;
		this.environment = environment;
	}

	/**
	 * Check if actions match.
	 * @param action the action to check
	 * @return if the current action is equal.
	 */
	@Override
	public boolean isAction(Object action) {
		return this.action.equals(action);
	}

	@Override
	public boolean isSubject(Object user) {
		if (getSubject() == null || user == null) {
			return false;
		}

		if (!(user instanceof UserDetails)) {
			return false;
		}

		if (!(getSubject() instanceof UserDetails)) {
			return false;
		}

		return ((UserDetails) user).getUsername()
				.equals(((UserDetails) getSubject()).getUsername());
	}

}