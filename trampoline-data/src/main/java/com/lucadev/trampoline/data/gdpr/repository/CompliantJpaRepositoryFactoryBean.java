package com.lucadev.trampoline.data.gdpr.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Custom {@link JpaRepositoryFactoryBean} used to add custom advice.
 *
 * @param <R> repository type
 * @param <T> entity type
 * @param <I> entity primary key type
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Slf4j
public class CompliantJpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {

	@Setter
	@Getter
	@Autowired // autowired is required due to not being able to constructor inject this
	// bean.
	private CompliantRepositoryProxyPostProcessor compliantRepositoryProxyPostProcessor;

	/**
	 * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
	 * @param repositoryInterface must not be {@literal null}.
	 */
	public CompliantJpaRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		log.debug("Creating GDPR compliant repository factory support.");
		RepositoryFactorySupport factory = super.createRepositoryFactory(em);
		factory.addRepositoryProxyPostProcessor(
				this.compliantRepositoryProxyPostProcessor);
		return factory;
	}

}
