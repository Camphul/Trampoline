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

	/**
	 * Processes a repository proxy to add GDPR support where needed.
	 * @param factory the proxy factory.
	 * @param repositoryInformation repository information.
	 */
	@Override
	public void postProcess(ProxyFactory factory,
							RepositoryInformation repositoryInformation) {
		// Check if we need to register a method interceptor on given repository.
		Streamable<Method> methods = repositoryInformation.getQueryMethods()
				.filter(this::hasAnyPersonalData);

		// If no param in any method in the repo is annotated using @PersonalData do not
		// add the interceptor at all.
		if (methods.isEmpty()) {
			return;
		}

		log.info("Adding GDPR compliant repository advice to {}.",
				repositoryInformation.getRepositoryBaseClass().getName());
		// Register the interceptor for each query method inside the repository.
		methods.forEach(
				this.compliantRepositoryMethodInterceptor::registerQueryMethodForIntercept);

		// Finally adds the interceptor advice to the proxy factory resulting in the GDPR
		// compliance being added.
		factory.addAdvice(this.compliantRepositoryMethodInterceptor);
	}

	/**
	 * Checks if a method contains any parameter annotated with the {@link PersonalData}
	 * annotation.
	 * @param method method to inspect.
	 * @return true when atleast one or more method parameters is annotated using
	 * {@link PersonalData}.
	 */
	private boolean hasAnyPersonalData(Method method) {
		return Arrays.stream(method.getParameters())
				.anyMatch(parameter -> parameter.isAnnotationPresent(PersonalData.class));
	}

}
