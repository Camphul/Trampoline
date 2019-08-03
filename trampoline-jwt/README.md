# trampoline-jwt

Basic setup for enabling JWT authentication/authorization on Spring Security.
 
This module is completely decoupled from [trampoline-security](../trampoline-security) and can be used on existing spring security implementations.

## Architecture

This module embeds jwt authorization through the use of standard Spring interfaces such as:

- `AuthenticationEntryPoint`: `JwtAuthenticationEntryPoint` denies all requests by default
- `AuthenticationProvider`: `JwtAuthenticationProvider` accepts our own `AuthenticationToken` implementation
- `AbstractAuthenticationToken`: `JwtAuthenticationToken` contains the `JwtPayload` using for authorization
- `OncePerRequestFilter`: `JwtAuthorizationFilter` which is basically a security filter which invokes the `JwtAuthenticationProvider` through the `AuthenticationManager`

We then have the following interfaces and classes to work with:

- `JwtConfiguration`: Implement this to manage JWT token creation.
- `JwtPayload`: POJO of a JWT token.
- `TokenService`: implemented by `JwtTokenService` to manage tokens, obtain claims, etc...

By implementing already existing Spring interface we can easily switch authorization without having any deeply coupled jwt code in our projects.

If you also use `trampoline-security` please add the `trampoline-jwt-security` module to your dependencies.
This module provides web controllers and missing components to make the link flawless.

## Configuration

- `trampoline.security.jwt.secret`: jwt signing secret, default is trampolineSecret
- `trampoline.security.jwt.tokenTimeout`: timeout property of jwt, default is 3600L
- `trampoline.security.jwt.header`: Header key containing our auth token.
- `trampoline.security.jwt.header-schema`: Schema prefix inside the header value(Bearer)
- `trampoline.security.jwt.claims.username`: claim key for username
- `trampoline.security.jwt.claims.authorities`: claim key for authorities
- `trampoline.security.jwt.claims.ignore-expiration`: claim key for ignoring expiration

## Handling custom claims

If you wish to add custom claims to the token you may implement `JwtConfigurationAdapter`(with `@Configuration` of course).

This interface contains methods which are invoked when creating a token.
These claims can be retrieved from the `Authentication` object by casting `Authentication` to `JwtAuthenticationToken`.
The parsed JWT will be available by invoking `JwtAuthenticationToken#getJwtPayload()`.