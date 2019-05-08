package com.lucadev.trampoline.web.autoconfigure;

import com.lucadev.trampoline.web.converter.UUIDConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

/**
 * Autoconfiguration to provide a {@link UUIDConverter} bean.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Configuration
@ConditionalOnClass(Converter.class)
public class UuidConverterAutoConfiguration {

	/**
	 * Bean definition for our {@link UUID} converter. In this case our
	 * {@link UUIDConverter}
	 * @return a converter for converting a {@link String} into a {@link UUID}
	 */
	@Bean
	@ConditionalOnMissingBean
	public Converter<String, UUID> uuidConverter() {
		return new UUIDConverter();
	}

}
