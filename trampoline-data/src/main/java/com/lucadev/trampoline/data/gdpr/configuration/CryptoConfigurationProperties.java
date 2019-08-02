package com.lucadev.trampoline.data.gdpr.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Data
@ConfigurationProperties(prefix = "trampoline.data.gdpr")
public class CryptoConfigurationProperties {

	@NotBlank
	@NotNull
	private String cipher = "AES/ECB/PKCS5Padding";

	@NotNull
	@NotBlank
	private String algorithm = "AES";

	// Crypto key
	@NotBlank
	@NotNull
	@Length(min = 1)
	private String key = "MySuperSecretKey";

}
