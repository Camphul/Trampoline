package com.lucadev.example.trampoline.service;

import com.lucadev.example.trampoline.persistence.entity.BlogPost;
import com.lucadev.trampoline.security.logging.handler.event.UserActivityEvent;
import com.lucadev.trampoline.security.logging.handler.event.UserActivityEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Listen for all user activity events which are related to BlogPost.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
@Slf4j
@Component
public class BlogPostUserActivityListener implements UserActivityEventListener {

	@Override
	public boolean filter(UserActivityEvent userActivityEvent) {
		return userActivityEvent.isActingUpon(BlogPost.class);
	}

	@Override
	public void handle(UserActivityEvent event) {
		log.info("BlogPost event: {}", event.getDescription());
	}

}
