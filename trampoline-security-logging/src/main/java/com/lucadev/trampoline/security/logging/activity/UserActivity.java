package com.lucadev.trampoline.security.logging.activity;

import com.lucadev.trampoline.security.logging.ActivityLayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.Authentication;

/**
 * A wrapper for activity triggered by a user.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@AllArgsConstructor
@Getter
@ToString
public class UserActivity {

	//Principal who triggered the action
	private final Authentication authentication;
	//Logging identifier to use
	private final String identifier;
	//Category which was configured through the annotation
	private final String category;
	//Which layer is it executed.
	private final ActivityLayer activityLayer;

	private final UserActivityInvocationContext invocationContext;

}
