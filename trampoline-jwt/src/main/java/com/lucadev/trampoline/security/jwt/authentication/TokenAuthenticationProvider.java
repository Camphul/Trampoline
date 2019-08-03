package com.lucadev.trampoline.security.jwt.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
/**
 * Authentication provider used to issue new tokens.
 * Support {@link UsernamePasswordAuthenticationToken}.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/3/19
 */
public interface TokenAuthenticationProvider extends AuthenticationProvider {

}
