package com.lucadev.trampoline.assetstore.autoconfigure;

import com.lucadev.trampoline.assetstore.web.converter.AssetResponseHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * A WebMvcConfigurer to configure custom HttpMessageConverter.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @see WebMvcConfigurer
 * @see HttpMessageConverter
 * @since 9-6-18
 */
@Slf4j
@Configuration
public class AssetStoreWebAutoConfiguration implements WebMvcConfigurer, Ordered {

	/**
	 * Order of the configuration to load.
	 */
	public static final int ASSET_STORE_CONFIGURATION_ORDER = 70;

	/**
	 * Registers our custom HttpMessageConverter.
	 * @param converters a List of currently registered HttpMessageConverter instances.
	 * @see HttpMessageConverter
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.debug("Adding AssetResponse message converter.");
		converters.add(new AssetResponseHttpMessageConverter());
	}

	@Override
	public int getOrder() {
		return ASSET_STORE_CONFIGURATION_ORDER;
	}

}
