package com.lucadev.trampoline.assetstore.configuration;

import com.lucadev.trampoline.assetstore.web.converter.AssetResponseHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.lucadev.trampoline.assetstore.configuration.AssetStoreWebConfiguration.ASSET_STORE_WEB_MVC_CONFIG_ORDER;

/**
 * A WebMvcConfigurer to configure custom HttpMessageConverter.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @see WebMvcConfigurer
 * @see HttpMessageConverter
 * @since 9-6-18
 */
@Configuration
@Order(ASSET_STORE_WEB_MVC_CONFIG_ORDER)
public class AssetStoreWebConfiguration implements WebMvcConfigurer {

	public static final int ASSET_STORE_WEB_MVC_CONFIG_ORDER = 70;

	/**
	 * Registers our custom HttpMessageConverter
	 *
	 * @param converters a List of currently registered HttpMessageConverter instances.
	 * @see HttpMessageConverter
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new AssetResponseHttpMessageConverter());
	}
}
