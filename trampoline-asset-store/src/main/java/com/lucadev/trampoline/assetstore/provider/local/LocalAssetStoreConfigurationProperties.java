package com.lucadev.trampoline.assetstore.provider.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties used by local asset store.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@ConfigurationProperties(prefix = "trampoline.assetstore.provider.local")
public class LocalAssetStoreConfigurationProperties {

	private String directory = "./local-asset-store/";

}
