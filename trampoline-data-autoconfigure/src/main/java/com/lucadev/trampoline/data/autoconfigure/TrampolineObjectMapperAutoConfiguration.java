package com.lucadev.trampoline.data.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Autoconfiguration for a jackson {@link ObjectMapper} bean.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 27-4-18
 */
@Configuration
@ConditionalOnMissingBean(ObjectMapper.class)
@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
public class TrampolineObjectMapperAutoConfiguration {

	/**
	 * Jackson {@link ObjectMapper} bean definition.
	 * Autoconfigures the Java 8 date/time serialization(JSR310).
	 * @return {@link ObjectMapper} bean
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.build();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return objectMapper;
	}

}
