package com.lucadev.trampoline.data.gdpr.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;

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
		log.info("Adding GDPR compliant repository advice.");
		factory.addAdvice(this.compliantRepositoryMethodInterceptor);
	}

}
