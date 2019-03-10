package com.lucadev.trampoline.security.logging.activity.resolver;

import com.lucadev.trampoline.security.logging.aop.InterceptedUserActivity;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Component
public class DefaultUserActivityResolver extends AbstractUserActivityResolver{
	@Override
	public String getReadableDescription(InterceptedUserActivity userActivity) {
		return "Activity: " + userActivity.getCategory() + "::" + userActivity.getIdentifier();
	}
}
