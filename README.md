# Trampoline
Jump straight into serious development through the use of multiple ready-to-use Spring Boot starters.

Please check [trampoline-example-app](/trampoline-example-app) for an example application.

## Project Status

**Extremely WIP:** I have no idea regarding future plans but want to keep working on this to prevent time wasted on rewriting boilerplate user services, permission services, etc...

## Modules/starters

Click on the link to the module to learn more.

### trampoline-common

The [trampoline-common](/trampoline-common) starter contains a bunch of services and components that aren't quite large enough to have their own starter.

### trampoline-data

The [trampoline-data](/trampoline-data) module expands on Spring Data JPA by providing base entities, repositories and more.
Easily audit all your entities and use UUID as primary key.

### trampoline-security

The [trampoline-security](/trampoline-security) module tries to implement Spring Security as much as possible by persisting users with the help of [trampoline-data](/trampoline-data) and a number of services.
 
This module does not implement any authentication/authorization mechanisms other than a service to programmatically authenticate against persisted users.

### trampoline-security-jwt

The [trampoline-security-jwt](/trampoline-security-jwt) module expands on the above [trampoline-security](/trampoline-security) by providing service implementations defined in [trampoline-security](/trampoline-security) through autoconfiguration.

This module implements an authorization filter, authentication service(sign-in), jwt refresh functionality(token expiry), and more.

### trampoline-starter

[trampoline-starter](/trampoline-starter) is a collection of trampoline starters quickly start development:

- [trampoline-common-starter](/trampoline-common-starter)
- [trampoline-data-starter](/trampoline-data-starter) 
- [trampoline-security-starter](/trampoline-security-starter)
- [trampoline-security-jwt-starter](/trampoline-security-jwt-starter)

### trampoline-X-web

Trampoline web modules such as [trampoline-security-jwt-web](/trampoline-security-jwt-web) register routes to use their functionality such as JWT endpoints.

## Contributing

If you wish to contribute please read the guidelines as described in [CONTRIBUTING.md](/CONTRIBUTING.md)

## License

This project is licensed under the [MIT License](/LICENSE.txt)