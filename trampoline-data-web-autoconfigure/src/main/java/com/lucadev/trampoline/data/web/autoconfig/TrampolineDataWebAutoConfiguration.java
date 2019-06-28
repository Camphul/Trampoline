package com.lucadev.trampoline.data.web.autoconfig;

import com.lucadev.trampoline.data.web.FindByIdMethodArgumentResolver;
import com.lucadev.trampoline.data.web.PrimaryKeyMapper;
import com.lucadev.trampoline.data.web.UUIDPrimaryKeyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfigure data web.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/28/19
 */
@Slf4j
@Configuration
@AutoConfigureBefore(TrampolineDataWebMvcAutoConfiguration.class)
@ConditionalOnClass(FindByIdMethodArgumentResolver.class)
@RequiredArgsConstructor
public class TrampolineDataWebAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public PrimaryKeyMapper primaryKeyMapper() {
		return new UUIDPrimaryKeyMapper();
	}

}
