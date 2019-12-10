package com.lucadev.trampoline.security.logging.handler.event;

import com.lucadev.trampoline.security.logging.UserActivity;
import com.lucadev.trampoline.security.logging.UserActivityInvocationContext;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

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
	public UserActivityEvent(UserActivityInvocationContext source,
			UserActivity userActivity) {
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
	 * Check if the description matches. Useful for filtering events.
	 * @param description activity description.
	 * @return if we match user activity description.
	 */
	public boolean isDescription(String description) {
		return getDescription().equals(description);
	}

	/**
	 * Same as {@link #getSource()} but with casting.
	 * @return user activity invocation context.
	 * @see #getSource()
	 */
	public UserActivityInvocationContext getInvocationContext() {
		return (UserActivityInvocationContext) getSource();
	}

	/**
	 * Get principal who invoked the event.
	 * @return the user who caused the event to invoke.
	 * @see UserDetails
	 * @see UserActivity#getPrincipal()
	 */
	public UserDetails getPrincipal() {
		return this.userActivity.getPrincipal();
	}

	/**
	 * Get object which is acted upon.
	 * @return acted upon
	 * @see com.lucadev.trampoline.security.logging.ActingUpon
	 * @see UserActivity#getActedUpon()
	 */
	public Object getActedUpon() {
		return this.userActivity.getActedUpon();
	}

	/**
	 * Check if an object is being acted upon.
	 * @return if acted upon object is not null.
	 */
	public boolean isActingUpon() {
		return getActedUpon() != null;
	}

	/**
	 * Check if the object acted upon is of a certain type. Returns false is no object is
	 * being acted upon.
	 * @param actedUponType the type of object being acted upon.
	 * @return if the object acted upon is an instance of the given type.
	 */
	public boolean isActingUpon(Class<?> actedUponType) {
		if (!isActingUpon()) {
			return false;
		}
		return actedUponType.isInstance(getActedUpon());
	}

}
