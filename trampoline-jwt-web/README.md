# trampoline-jwt-web

REST API endpoints for [trampoline-jwt](../trampoline-jwt)

## Architecture

Adds a simple controller on top of trampoline-jwt.

The base path for the control endpoints are configured by the `trampoline.security.jwt.web.base-mapping` property which defaults to `/auth`.

The authorization mapping on top of the base mapping can be configured using the `trampoline.security.jwt.web.authorize-mapping` property(default: `/authorize`.

The token refresh mapping on top of the base mapping can be configured using the `trampoline.security.jwt.web.refresh-mapping` property(default `/authorize`).

## Override controller

To override the jwt web controller create a `@RestController` which implements `AuthenticationController`