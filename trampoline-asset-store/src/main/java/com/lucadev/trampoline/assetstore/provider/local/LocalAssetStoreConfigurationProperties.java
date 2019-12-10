package com.lucadev.trampoline.assetstore.provider.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Properties used by local asset store.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Validated
@ConfigurationProperties(prefix = "trampoline.assetstore.provider.local")
public class LocalAssetStoreConfigurationProperties {

	@NotNull
	@NotBlank
	private String directory = "./local-asset-store/";

	@NotNull
	private boolean keepOnDiskWhenRemoved = false;

}
