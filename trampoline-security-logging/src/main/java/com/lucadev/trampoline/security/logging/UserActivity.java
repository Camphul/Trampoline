package com.lucadev.trampoline.security.logging;

import com.lucadev.trampoline.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A single user activity POJO.
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Getter
@AllArgsConstructor
@ToString
public class UserActivity {

	private final User principal;
	private final String identifier;
	private final String category;
	private final ActivityLayer activityLayer;
	private final UserActivityInvocationDetails invocationDetails;
	private final String description;

}
