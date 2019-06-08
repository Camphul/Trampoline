package com.lucadev.trampoline.assetstore.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties used by asset store.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@ConfigurationProperties(prefix = "trampoline.assetstore")
public class AssetStoreConfigurationProperties {

	private String provider = "local";

}
