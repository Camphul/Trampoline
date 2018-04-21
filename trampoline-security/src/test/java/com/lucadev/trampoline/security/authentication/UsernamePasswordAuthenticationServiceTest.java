package com.lucadev.trampoline.security.authentication;

import com.lucadev.trampoline.security.exception.AuthenticationException;
import com.lucadev.trampoline.security.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test the authentication mechanism which requires an username/password payload.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class UsernamePasswordAuthenticationServiceTest {

    private static final String USERNAME = "testuser";
    private static final String CREDENTIAL = "testpass";
    private UsernamePasswordAuthenticationService service;
    private AuthenticationManager authenticationManager;

    @Before
    public void before() {
        this.authenticationManager = new AuthenticationManagerMock(USERNAME, CREDENTIAL);
        this.service = new UsernamePasswordAuthenticationService(authenticationManager);
    }

    @After
    public void after() {
        this.service = null;
    }

    @Test
    public void shouldSucceedIsSupportedType() throws Exception {
        boolean expected = true;
        boolean result = this.service.isSupportedType(UsernamePasswordAuthenticationPayload.class);
        assertEquals(expected, result);
    }

    @Test
    public void shouldFailIsSupportedType() throws Exception {
        boolean expected = false;
        boolean result = this.service.isSupportedType(UnknownAuthenticationPayload.class);
        assertEquals(expected, result);
    }

    @Test
    public void shouldSucceedAuthenticate() throws Exception {
        Optional<User> result = this.service.authenticate(new UsernamePasswordAuthenticationPayload(USERNAME, CREDENTIAL));
        assertTrue(result.isPresent());
        User user = result.get();
        assertEquals(USERNAME, user.getUsername());
    }

    @Test(expected = AuthenticationException.class)
    public void shouldFailAuthenticateBecauseInvalidCredential() throws Exception {
        Optional<User> result = this.service.authenticate(new UsernamePasswordAuthenticationPayload(USERNAME, CREDENTIAL + CREDENTIAL));
    }

    @Test
    public void shouldFailAuthenticateBecauseInvalidPayloadType() throws Exception {
        Optional<User> result = this.service.authenticate(new UnknownAuthenticationPayload());
        assertFalse(result.isPresent());
    }

    protected static class UnknownAuthenticationPayload implements AuthenticationPayload {

        /**
         * Erase payload data
         */
        @Override
        public void erasePayload() {

        }
    }

}