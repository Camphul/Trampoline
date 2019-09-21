package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.event.AssetEventPublisher;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStoreConfigurationProperties;
import com.lucadev.trampoline.assetstore.provider.local.LocalAssetStoreFactory;
import com.lucadev.trampoline.assetstore.repository.AssetMetaDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@AutoConfigureBefore(AssetStoreAutoConfiguration.class)
@EnableConfigurationProperties({LocalAssetStoreConfigurationProperties.class})
public class LocalAssetStoreAutoConfiguration {

	private final LocalAssetStoreConfigurationProperties localAssetStoreConfigurationProperties;

	private final AssetMetaDataRepository metaDataRepository;

	private final AssetEventPublisher eventPublisher;

	/**
	 * Configure default asset store factory.
	 *
	 * @return a new {@link AssetStore}
	 */
	@Bean
	public LocalAssetStoreFactory localAssetStoreFactory() {
		log.debug("Creating local asset store factory bean.");
		return new LocalAssetStoreFactory(this.localAssetStoreConfigurationProperties,
				this.metaDataRepository, this.eventPublisher);
	}

}
