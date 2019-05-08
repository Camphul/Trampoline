package com.lucadev.trampoline.security.logging;

import com.lucadev.trampoline.security.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * User activity that took place.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Getter
@AllArgsConstructor
@ToString
public class UserActivity {

	private UserActivityInvocationDetails invocationDetails;

	/**
	 * User who caused the activity
	 */
	private final User principal;

	/**
	 * Description of the activity
	 */
	private final String description;

	/**
	 * On which object did the activity take place
	 */
	private final Object actedUpon;

}
