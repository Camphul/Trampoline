package com.lucadev.example.trampoline.service;

import com.lucadev.trampoline.security.logging.handler.event.UserActivityEvent;
import com.lucadev.trampoline.security.logging.handler.event.UserActivityEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Example of how you may listen to events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
@Slf4j
@Component
public class WhoAmIUserActivityListener implements UserActivityEventListener {

	@Override
	public boolean filter(UserActivityEvent userActivityEvent) {
		return userActivityEvent.isDescription("GET_WHO_AM_I");
	}

	@Override
	public void handle(UserActivityEvent event) {
		log.info("Handled GET_WHO_AM_I user activity.");
	}
}
