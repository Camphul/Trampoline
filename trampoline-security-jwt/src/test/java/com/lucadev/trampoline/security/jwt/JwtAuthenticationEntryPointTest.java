package com.lucadev.trampoline.security.jwt;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test the authentication entry point
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class JwtAuthenticationEntryPointTest {


    @Test
    public void shouldSucceedSendUnauthorizedErrorResponse() throws IOException, ServletException {
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        entryPoint.commence(mockRequest, mockResponse, null);//Validate that all responses are set unauthorized

        //Does not matter which text response, as long as it's UNAUTHORIZED
        verify(mockResponse, atLeastOnce()).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), any());
    }

}