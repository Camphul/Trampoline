package com.lucadev.trampoline.security.jwt.support.web.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for jwt web module.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@Data
@ConfigurationProperties(prefix = "trampoline.security.jwt.web")
public class JwtWebConfigurationProperties {

	// Base mapping
	private String baseMapping = "/auth";

	// Mapping for authorizing(baseMapping will be prepended)
	private String authorizeMapping = "/authorize";

	// Mapping for token refresh(baseMapping will be prepended)
	private String refreshMapping = "/refresh";

}
