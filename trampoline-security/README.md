# trampoline-security

Module that expands upon some basic Spring Security functionality together with trampoline-data to store identities.

## Architecture

trampoline-security is the base module to implement security in your trampoline based application.

This module is probably the most opinionated as the `User` class is a `TrampolineEntity` and implements Spring's `UserDetails`.
Each `User` can have 0 or more roles assigned(in the form of a `Role`). Each role may contain 0 or more privileges(in the form of a `Privilege`).

There's service's for each component:

- `UserService`: extends Spring's `UserDetailsService`
- `RoleService`: manages roles
- `PrivilegeService`: manages privileges
- `UserAuthenticationService`: separate service dedicated to manage user's passwords(through the use of Spring's `PasswordEncoder`) and validate the state of the `User`. Check if it's still enabled, etc..

## Authentication mechanism

trampoline-security's implementation of `UserDetailsService` allows you to configure two ways of loading users.
You may load users by `username` only or load them by `username` or `email`. This can be configured with the following property:

```properties
trampoline.security.allow-email-identification=true
``` 

The default value for this property is `false`.



### Configuring authorization scheme

Roles and privileges can be configured in Java by implementing the `AuthorizationSchemeConfiguration`.
Add the `@Configuration` annotation to your implementation to enable the service.
Use the `@ConditionalOn*` and `@Profile` annotations to disable or enable scheme importing.

### Logging user activity

Please make sure you have the [trampoline-security-logging-starter](../trampoline-security-logging-starter) if you wish to add `User` activity logging.
To enable logging add the `@EnableUserActivityLogging` annotation.

To log an activity add the `@LogUserActivity` above a method and specify the identifier, category and activity layer.

If you wish to handle all activities that are generated please implement the `UserActivityHandler` interface.

By default all activity is summarized and logged to the console.