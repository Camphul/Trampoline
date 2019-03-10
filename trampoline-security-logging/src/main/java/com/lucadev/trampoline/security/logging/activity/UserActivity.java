package com.lucadev.trampoline.security.logging.activity;

import com.lucadev.trampoline.security.logging.ActivityLayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A single user activity.
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
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
	private final UserActivityMethodDetails invocationDetails;
	private final String description;


}
