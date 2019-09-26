package com.lucadev.trampoline.security.abac.decorator;

import com.lucadev.trampoline.service.time.TimeProvider;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Policy environment decorator which adds time.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9/26/19
 */
@RequiredArgsConstructor
public class TimePolicyEnvironmentDecorator implements PolicyEnvironmentDecorator {

	private final TimeProvider timeProvider;

	@Override
	public void decorate(Map<String, Object> environment) {
		environment.put("time", timeProvider.unix());
	}

}
