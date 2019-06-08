package com.lucadev.trampoline.assetstore.provider.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties used by local asset store.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "trampoline.assetstore.provider.local")
public class LocalAssetStoreConfigurationProperties {

	private String directory = "./local-asset-store/";

}
