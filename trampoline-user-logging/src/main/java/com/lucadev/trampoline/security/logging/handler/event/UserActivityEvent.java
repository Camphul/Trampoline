package com.lucadev.trampoline.security.logging.handler.event;

import com.lucadev.trampoline.security.logging.UserActivity;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Application event for user activity.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 * @see UserActivity
 * @see ApplicationEvent
 */
@Data
public class UserActivityEvent extends ApplicationEvent {

	private final UserActivity userActivity;

	/**
	 * Construct a new user activity event.
	 * @param source event source.
	 * @param userActivity user activity.
	 */
	public UserActivityEvent(Object source, UserActivity userActivity) {
		super(source);
		this.userActivity = userActivity;
	}

	/**
	 * Get user activity description.
	 * @return user activity description.
	 */
	public String getDescription() {
		return this.userActivity.getDescription();
	}

	/**
	 * Check if the description matches.
	 * Useful for filtering events.
	 * @param description activity description.
	 * @return if we match user activity description.
	 */
	public boolean isDescription(String description) {
		return getDescription().equals(description);
	}
}
