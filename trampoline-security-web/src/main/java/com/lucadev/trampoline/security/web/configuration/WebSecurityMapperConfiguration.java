package com.lucadev.trampoline.security.web.configuration;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

/**
 * Reusable MapStruct mapper config for security based mappings.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/18/19
 */
@MapperConfig(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
		componentModel = "spring")
public class WebSecurityMapperConfiguration {

}
