package com.lucadev.example.trampoline.service;

import com.lucadev.trampoline.security.logging.activity.UserActivity;
import com.lucadev.trampoline.security.logging.activity.handler.UserActivityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Service
public class ExampleUserActivityHandler implements UserActivityHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ExampleUserActivityHandler.class);

	@Override
	public void handleUserActivity(UserActivity userActivity) {
		LOG.info("Receive activity: {}::{}", userActivity.getCategory(), userActivity.getIdentifier());
	}
}
