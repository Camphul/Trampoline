package com.lucadev.trampoline.notify.email.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuration for trampoline-notify email service.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@EnableAsync
@Configuration
@EnableConfigurationProperties(EmailConfigurationProperties.class)
public class EmailConfiguration {

}
