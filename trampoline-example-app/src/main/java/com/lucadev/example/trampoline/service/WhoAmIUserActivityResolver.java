package com.lucadev.example.trampoline.service;

import com.lucadev.trampoline.security.logging.aop.InterceptedUserActivity;
import com.lucadev.trampoline.security.logging.resolver.AbstractUserActivityResolver;
import org.springframework.stereotype.Component;

/**
 * Resolver to map user activity on whoami
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Component
public class WhoAmIUserActivityResolver extends AbstractUserActivityResolver {
	@Override
	public String getReadableDescription(InterceptedUserActivity userActivity) {
		return "Access /whoami";
	}
}
