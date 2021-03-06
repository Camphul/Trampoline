package com.lucadev.trampoline.security.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User activity that took place.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Getter
@AllArgsConstructor
@ToString
public final class UserActivity {

	/**
	 * Context in which the activity took place.
	 */
	private UserActivityInvocationContext invocationContext;

	/**
	 * User who caused the activity.
	 */
	private final UserDetails principal;

	/**
	 * Description of the activity.
	 */
	private final String description;

	/**
	 * On which object did the activity take place.
	 */
	private final Object actedUpon;

}
