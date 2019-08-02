package com.lucadev.trampoline.data.gdpr.repository;

import com.lucadev.trampoline.data.gdpr.PersonalData;
import com.lucadev.trampoline.data.gdpr.crypto.StringCrypto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Parameter;

/**
 * Method interceptor which encrypts if necessary.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Slf4j
@RequiredArgsConstructor
public class CompliantRepositoryMethodInterceptor implements MethodInterceptor {

	private final StringCrypto stringCrypto;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Parameter[] parameters = invocation.getMethod().getParameters();
		Object[] arguments = invocation.getArguments();
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			// Check if we need to encrypt before execution
			if (parameter.isAnnotationPresent(PersonalData.class)) {
				if (!parameter.getType().equals(String.class)) {
					throw new IllegalArgumentException("Argument \"" + parameter.getName()
							+ "\" with @PersonalData must be of type String.");
				}
				// Encrypt
				arguments[i] = this.stringCrypto.encrypt((String) arguments[i]);
				log.debug("Encrypted repository method argument in intercept.");
			}
		}
		// Proceed
		return invocation.proceed();
	}

}
