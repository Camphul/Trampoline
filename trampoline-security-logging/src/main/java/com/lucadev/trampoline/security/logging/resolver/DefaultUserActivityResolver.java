package com.lucadev.trampoline.security.logging.resolver;

import com.lucadev.trampoline.security.logging.aop.InterceptedUserActivity;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Component
public class DefaultUserActivityResolver extends AbstractUserActivityResolver{
	@Override
	public String getReadableDescription(InterceptedUserActivity userActivity) {
		return "Activity: " + userActivity.getCategory() + "::" + userActivity.getIdentifier();
	}
}
