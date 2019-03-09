package com.lucadev.example.trampoline.service;

import com.lucadev.example.trampoline.messaging.UserActivityMessage;
import com.lucadev.trampoline.security.logging.activity.UserActivity;
import com.lucadev.trampoline.security.logging.activity.handler.UserActivityHandler;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * POC for messaging queue/task queue
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Service
@AllArgsConstructor
public class ExampleUserActivityHandler implements UserActivityHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ExampleUserActivityHandler.class);
	private final JmsTemplate jmsTemplate;

	@Override
	public void handleUserActivity(UserActivity userActivity) {
		LOG.info("Sending activity: {}::{}", userActivity.getCategory(), userActivity.getIdentifier());
		LOG.info("Current security context: {}", SecurityContextHolder.getContext().getAuthentication());
		jmsTemplate.convertAndSend("useractivity", new UserActivityMessage(userActivity));
	}
}
