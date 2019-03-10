package com.lucadev.example.trampoline.service;

import com.lucadev.trampoline.security.logging.activity.resolver.AbstractUserActivityResolver;
import com.lucadev.trampoline.security.logging.aop.InterceptedUserActivity;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Component
public class WhoamiUserActivityResolver extends AbstractUserActivityResolver {
	@Override
	public String getReadableDescription(InterceptedUserActivity userActivity) {
		return "WhoAmI resolver for activity " + userActivity.getCategory() + "::" + userActivity.getIdentifier();
	}
}
