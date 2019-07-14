package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.AssetStore;
import com.lucadev.trampoline.assetstore.event.AssetEventPublisher;
import com.lucadev.trampoline.assetstore.event.DefaultAssetEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfig for asset store events.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7/14/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(AssetStore.class)
@AutoConfigureBefore(AssetStoreAutoConfiguration.class)
public class AssetEventPublisherAutConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public AssetEventPublisher assetEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		return new DefaultAssetEventPublisher(applicationEventPublisher);
	}

}
