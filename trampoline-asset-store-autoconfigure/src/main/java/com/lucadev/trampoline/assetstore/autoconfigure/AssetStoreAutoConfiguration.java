package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.AssetStoreFactory;
import com.lucadev.trampoline.assetstore.configuration.AssetStoreConfigurationProperties;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStoreConfigurationProperties;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStoreFactory;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class AssetStoreAutoConfiguration {

	/**
	 * Configure default asset store.
	 * @return a new {@link AssetStore}
	 * @throws Exception when we cannot resolve an implementation.
	 */
	@Bean
	@ConditionalOnMissingBean(AssetStore.class)
	public AssetStore assetStore(List<AssetStoreFactory> factories, AssetStoreConfigurationProperties configurationProperties) throws Exception {
		if (factories.isEmpty()) {
			throw new NullPointerException(
					"Could not find matching factories for AssetStore");
		}

		AssetStoreFactory factory = factories.stream()
				.filter(factoryPredicate -> factoryPredicate
						.supports(configurationProperties.getProvider()))
				.findFirst().orElseThrow(() -> new NullPointerException(
						"Could not find matching factory."));
		// found factory
		return factory.getObject();
	}

	/**
	 * Configure default asset store factory.
	 * @return a new {@link AssetStore}
	 */
	@Bean
	public LocalAssetStoreFactory localAssetStoreFactory(LocalAssetStoreConfigurationProperties localAssetStoreConfigurationProperties,
														 AssetMetaDataRepository metaDataRepository) {
		log.debug("Creating local asset store factory bean.");
		return new LocalAssetStoreFactory(localAssetStoreConfigurationProperties, metaDataRepository);
	}

}
