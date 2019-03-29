package com.lucadev.trampoline.security.logging;

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

	private final String principal;
	private final String identifier;
	private final String category;
	private final ActivityLayer activityLayer;
	private final UserActivityInvocationDetails invocationDetails;
	private final String description;

}
