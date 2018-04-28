package com.lucadev.trampoline.security.jwt.web.controller;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.web.model.JwtAuthenticationResponse;
import com.lucadev.trampoline.security.jwt.web.model.UserAuthenticationRequest;
import com.lucadev.trampoline.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@RestController
@RequestMapping("${trampoline.security.jwt.authPath:/auth}")
@AllArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationService;
    private final TokenService tokenService;

    /**
     * Login with username-password
     *
     * @param userAuthenticationRequest
     * @return
     */
    @PostMapping("${trampoline.security.jwt.authPath.authorize:/authorize}")
    public JwtAuthenticationResponse submitAuthenticationTokenRequest(
            @RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        Authentication authentication = authenticationService.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getUsername(),
                        userAuthenticationRequest.getPassword()));

        if (authentication == null || !authentication.isAuthenticated()) {
            return new JwtAuthenticationResponse(false, "", "Could not authenticate user.");
        }
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return new JwtAuthenticationResponse(false, "", "Could not authenticate user.");
        }

        String token = tokenService.createToken(user);

        if (token == null || token.isEmpty()) {
            return new JwtAuthenticationResponse(false, null, "Could not authenticate user");
        }
        return new JwtAuthenticationResponse(true, token, "ok");
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
            //Catch exception to always return correct format
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new JwtAuthenticationResponse(false, null, e.getMessage());
        }
    }


}
