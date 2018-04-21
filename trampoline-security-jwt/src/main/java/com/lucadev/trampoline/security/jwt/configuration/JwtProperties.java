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

    //Signing secret
    private String secret;
    //Signing algorithm
    private String signingAlgorithm;
    //HTTP Header containing the token
    private String tokenHeader;
    //Timeout for token invalidation
    private long tokenTimeout;
    //Auth basepath
    private String authPath;
    //Token header data prefix
    private String tokenHeaderPrefix;
}
