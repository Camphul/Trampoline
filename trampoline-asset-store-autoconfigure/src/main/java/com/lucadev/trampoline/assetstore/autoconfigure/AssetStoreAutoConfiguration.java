package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.AssetStoreFactory;
import com.lucadev.trampoline.assetstore.configuration.AssetStoreConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Autoconfigure asset store implementation.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 9-6-18
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(AssetStore.class)
@EnableConfigurationProperties({AssetStoreConfigurationProperties.class})
public class AssetStoreAutoConfiguration {

	private final List<AssetStoreFactory> factories;

	private final AssetStoreConfigurationProperties configurationProperties;

	/**
	 * Configure default asset store.
	 * @return a new {@link AssetStore}
	 * @throws Exception when we cannot resolve an implementation.
	 */
	@Bean
	@ConditionalOnMissingBean(AssetStore.class)
	public AssetStore assetStore() throws Exception {
		if (this.factories.isEmpty()) {
			log.warn("No AssetStore factories discovered.");
			throw new NullPointerException(
					"Could not find matching factories for AssetStore");
		}

		AssetStoreFactory factory = this.factories.stream()
				.filter(factoryPredicate -> factoryPredicate
						.supports(this.configurationProperties.getProvider()))
				.findFirst().orElseThrow(() -> new NullPointerException(
						"Could not find matching factory."));

		log.info("Using factory {}", factory, getClass().getName());
		// found factory
		return factory.getObject();
	}

}
