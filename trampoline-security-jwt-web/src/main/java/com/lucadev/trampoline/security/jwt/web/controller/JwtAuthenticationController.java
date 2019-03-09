package com.lucadev.trampoline.security.jwt.web.controller;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.web.model.JwtAuthenticationResponse;
import com.lucadev.trampoline.security.jwt.web.model.UserAuthenticationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * REST model for JWT auth related endpoints.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@RestController
@RequestMapping("${trampoline.security.jwt.authPath:/auth}")
@AllArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Login with username-password
     *
     * @param userAuthenticationRequest
     * @return
     */
    @PostMapping("${trampoline.security.jwt.authPath.authorize:/authorize}")
    public JwtAuthenticationResponse submitAuthenticationTokenRequest(
            @Valid @RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getIdentifier(),
                            userAuthenticationRequest.getPassword()));

            if (authentication == null) {
                return new JwtAuthenticationResponse(false, null, "Could not authenticate user");
            }
            return new JwtAuthenticationResponse(true, String.valueOf(authentication.getDetails()), "ok");
        } catch (Exception ex) {
            return new JwtAuthenticationResponse(false, null, ex.getMessage());
        }
    }

    /**
     * Refresh token from current logged in request
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("${trampoline.security.jwt.authPath.refresh:/refresh}")
    public JwtAuthenticationResponse submitAuthenticationTokenRefreshRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshedToken = tokenService.processTokenRefreshRequest(request);
            return new JwtAuthenticationResponse(true, refreshedToken, "ok");
        } catch (Exception e) {
            //Catch model to always return correct format
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new JwtAuthenticationResponse(false, null, e.getMessage());
        }
    }


}
