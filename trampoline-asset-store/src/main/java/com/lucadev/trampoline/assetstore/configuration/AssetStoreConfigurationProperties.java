package com.lucadev.trampoline.assetstore.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties used by asset store.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Configuration
@EnableConfigurationProperties(AssetStoreConfigurationProperties.class)
@ConfigurationProperties(prefix = "trampoline.assetstore")
public class AssetStoreConfigurationProperties {

	private String provider = "local";

}
