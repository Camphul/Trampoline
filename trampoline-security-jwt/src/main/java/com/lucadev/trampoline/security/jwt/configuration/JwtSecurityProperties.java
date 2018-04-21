package com.lucadev.trampoline.security.jwt.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * Config values for JWT. Has immutable setters
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ConfigurationProperties(prefix = "trampoline.security.jwt")
public class JwtSecurityProperties {

    public static final String DEFAULT_SECRET = "trampolineSecret";
    public static final String DEFAULT_SIGNING_ALGORITHM = "HS512";
    public static final String DEFAULT_TOKEN_HEADER = "Authorization";
    public static final String DEFAULT_TOKEN_HEADER_PREFIX = "Bearer";
    public static final long DEFAULT_TOKEN_TIMEOUT = 3600L;
    public static final String DEFAULT_AUTH_PATH = "/auth";
    /**
     * Errormessage that gets thrown when you try to modify this object through a setter method.
     */
    private static final String IMMUTABILITY_ERROR_MESSAGE = "Cannot set prop of immutable config properties!";

    //Signing secret
    private String secret;
    //Signing algorithm
    private String signingAlgorithm;
    //HTTP Header containing the token
    private String tokenHeader;
    //Token header data prefix
    private String tokenHeaderPrefix;
    //Timeout for token invalidation
    private long tokenTimeout;
    //Auth basepath
    private String authPath;

    /**
     * Handle default cases if a value has not been set yet
     */
    @PostConstruct
    public void init() {
        secret = handleDefault(secret, DEFAULT_SECRET);
        signingAlgorithm = handleDefault(signingAlgorithm, DEFAULT_SIGNING_ALGORITHM);
        tokenHeader = handleDefault(tokenHeader, DEFAULT_TOKEN_HEADER);
        tokenHeaderPrefix = handleDefault(tokenHeaderPrefix, DEFAULT_TOKEN_HEADER_PREFIX);
        if (tokenTimeout <= 0) {
            tokenTimeout = 3600L;
        }
        authPath = handleDefault(authPath, DEFAULT_AUTH_PATH);
    }

    private String handleDefault(String prop, String defaultValue) {
        if (prop == null || prop.isEmpty()) {
            return defaultValue;
        }
        return prop;
    }

    public void setSecret(String secret) {
        if (this.secret != null) {
            throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
        }
        this.secret = secret;
    }

    public void setSigningAlgorithm(String signingAlgorithm) {
        if (this.signingAlgorithm != null) {
            throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
        }
        this.signingAlgorithm = signingAlgorithm;
    }

    public void setTokenHeader(String tokenHeader) {
        if (this.tokenHeader != null) {
            throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
        }
        this.tokenHeader = tokenHeader;
    }

    public void setTokenHeaderPrefix(String tokenHeaderPrefix) {
        if (this.tokenHeaderPrefix != null) {
            throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
        }
        this.tokenHeaderPrefix = tokenHeaderPrefix;
    }

    public void setTokenTimeout(long tokenTimeout) {
        if (this.tokenTimeout > 0) {
            throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
        }
        this.tokenTimeout = tokenTimeout;
    }

    public void setAuthPath(String authPath) {
        if (this.authPath != null) {
            throw new IllegalStateException(IMMUTABILITY_ERROR_MESSAGE);
        }
        this.authPath = authPath;
    }
}
