package com.lucadev.trampoline.assetstore.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configures AssetStore.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Configuration
@EnableConfigurationProperties(AssetStoreConfigurationProperties.class)
public class AssetStoreConfiguration {
}
