package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStoreConfigurationProperties;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStoreFactory;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfig for {@link LocalAssetStoreFactory}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(AssetStore.class)
public class LocalAssetStoreFactoryAutoConfiguration {

	private final LocalAssetStoreConfigurationProperties configurationProperties;

	private final AssetMetaDataRepository metaDataRepository;

	/**
	 * Configure default asset store.
	 * @return a new {@link AssetStore}
	 */
	@Bean
	public LocalAssetStoreFactory localAssetStoreFactory() {
		log.debug("Creating local asset store factory bean.");
		return new LocalAssetStoreFactory(configurationProperties, metaDataRepository);
	}

}
