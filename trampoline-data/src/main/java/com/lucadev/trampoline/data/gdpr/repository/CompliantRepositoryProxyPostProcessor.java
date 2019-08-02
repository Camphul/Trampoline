package com.lucadev.trampoline.data.gdpr.repository;

import com.lucadev.trampoline.data.gdpr.PersonalData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.data.util.Streamable;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Registers our advice to repository interfaces.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Slf4j
@RequiredArgsConstructor
public class CompliantRepositoryProxyPostProcessor
		implements RepositoryProxyPostProcessor {

	private final CompliantRepositoryMethodInterceptor compliantRepositoryMethodInterceptor;

	@Override
	public void postProcess(ProxyFactory factory,
			RepositoryInformation repositoryInformation) {
		// Check if we need to register a method interceptor on given repository.
		Streamable<Method> methods = repositoryInformation.getQueryMethods()
				.filter(this::hasAnyPersonalData);
		if (methods.isEmpty()) {
			return;
		}
		log.info("Adding GDPR compliant repository advice.");
		methods.forEach(
				this.compliantRepositoryMethodInterceptor::registerQueryMethodForIntercept);
		factory.addAdvice(this.compliantRepositoryMethodInterceptor);
	}

	private boolean hasAnyPersonalData(Method method) {
		return Arrays.stream(method.getParameters())
				.anyMatch(parameter -> parameter.isAnnotationPresent(PersonalData.class));
	}

}
