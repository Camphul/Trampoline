package com.lucadev.trampoline.security.jwt.adapter.security.web.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Configuration properties for jwt web module.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@Validated
@ConfigurationProperties(prefix = "trampoline.security.jwt.web")
public class JwtWebConfigurationProperties {

	// Base mapping
	@NotNull
	@NotBlank
	private String baseMapping = "/auth";

	// Mapping for authorizing(baseMapping will be prepended)
	@NotNull
	@NotBlank
	private String authorizeMapping = "/authorize";

	// Mapping for token refresh(baseMapping will be prepended)
	@NotNull
	@NotBlank
	private String refreshMapping = "/refresh";

	@NotNull
	@NotBlank
	private String userMapping = "/user";

}
