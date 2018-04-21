package com.lucadev.trampoline.security.jwt.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ConfigurationProperties(prefix = "trampoline.security.jwt")
public class JwtProperties {

    private String secret;
    private String signingAlgorithm;
    private String tokenHeader;
    private long tokenTimeout;
    private String authPath;
    private String tokenHeaderPrefix;
}
