package com.lucadev.trampoline.security.jwt.web.controller;

import com.lucadev.trampoline.security.jwt.TokenService;
import com.lucadev.trampoline.security.jwt.authentication.AuthenticationService;
import com.lucadev.trampoline.security.jwt.authentication.MemePayload;
import com.lucadev.trampoline.security.jwt.authentication.UsernamePasswordAuthenticationPayload;
import com.lucadev.trampoline.security.jwt.model.AuthenticationTokenResponse;
import com.lucadev.trampoline.security.jwt.model.UserAuthenticationRequest;
import com.lucadev.trampoline.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
@RestController
@RequestMapping("${trampoline.security.jwt.authpath:/auth}")
@AllArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    /**
     * Login with username-password
     *
     * @param userAuthenticationRequest
     * @return
     */
    @PostMapping("${trampoline.security.jwt.authPath.authorize:/authorize}")
    public AuthenticationTokenResponse submitAuthenticationTokenRequest(
            @RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        Optional<User> user = authenticationService.authenticate(
                new UsernamePasswordAuthenticationPayload(userAuthenticationRequest.getUsername(),
                        userAuthenticationRequest.getPassword()));
        if(!user.isPresent()) {
            return new AuthenticationTokenResponse(false, "", "Could not authenticate user.");
        }
        String token = tokenService.createToken(user.get());

        if (token == null || token.isEmpty()) {
            return new AuthenticationTokenResponse(false, null, "Could not authenticate user");
        }
        return new AuthenticationTokenResponse(true, token, "ok");
    }

    /**
     * Refresh token from current logged in request
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("${trampoline.security.jwt.authPath.refresh:/refresh}")
    public AuthenticationTokenResponse submitAuthenticationTokenRefreshRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshedToken = tokenService.processTokenRefreshRequest(request);
            return new AuthenticationTokenResponse(true, refreshedToken, "ok");
        } catch (Exception e) {
            //Catch exception to always return correct format
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new AuthenticationTokenResponse(false, null, e.getMessage());
        }
    }


}
