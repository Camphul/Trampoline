package com.lucadev.example.trampoline.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * POC for messaging queue/task queue
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Component
public class ExampleReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(ExampleReceiver.class);

	@JmsListener(destination = "useractivity", containerFactory = "userActivityMessageFactory")
	public void receiveMessage(UserActivityMessage message) {
		LOG.info("JMS Listener received: {}", message);
		LOG.info("Current security context: {}", SecurityContextHolder.getContext().getAuthentication());
	}
}
