# trampoline-jwt-security

REST API endpoints for [trampoline-jwt](../trampoline-jwt) which work with [trampoline-security](../trampoline-security)

Use the `@EnableJwtSecuritySupport` annotation to hook the jwt and security modules together.

## Architecture

Adds a simple controller on top of trampoline-jwt.

The base path for the control endpoints are configured by the `trampoline.security.jwt.web.base-mapping` property which defaults to `/auth`.

The authorization mapping on top of the base mapping can be configured using the `trampoline.security.jwt.web.authorize-mapping` property(default: `/authorize`.

The token refresh mapping on top of the base mapping can be configured using the `trampoline.security.jwt.web.refresh-mapping` property(default `/authorize`).

The user mapping on top of the base mapping can be configured using the `trampoline.security.jwt.web.user-mapping` property(default `/authorize`).

This module binds together the security and jwt modules incase you wish to create a monolithic application.

## Override controller

To override the jwt web controller create a `@RestController` which implements `AuthenticationController`