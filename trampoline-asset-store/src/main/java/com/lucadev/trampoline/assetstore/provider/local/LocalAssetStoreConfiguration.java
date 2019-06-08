package com.lucadev.trampoline.assetstore.provider.local;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for {@link LocalAssetStore}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Configuration
@EnableConfigurationProperties(LocalAssetStoreConfigurationProperties.class)
public class LocalAssetStoreConfiguration {

}
