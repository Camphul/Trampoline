# trampoline-security-jwt-web

REST API endpoints for [trampoline-security-jwt](../trampoline-security-jwt)

## Architecture

Adds a simple controller on top of trampoline-security-jwt.

The base path for the control endpoints are configured by the `trampoline.security.jwt.web.base-mapping` property which defaults to `/auth`.

The authorization mapping on top of the base mapping can be configured using the `trampoline.security.jwt.web.authorize-mapping` property(default: `/authorize`.

The token refresh mapping on top of the base mapping can be configured using the `trampoline.security.jwt.web.refresh-mapping` property(default `/authorize`).