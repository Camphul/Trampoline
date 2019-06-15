package com.lucadev.trampoline.assetstore.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Configuration properties used by asset store.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Validated
@ConfigurationProperties(prefix = "trampoline.assetstore")
public class AssetStoreConfigurationProperties {

	@NotNull
	@NotBlank
	private String provider = "local";

}
