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

### Configuring authorization scheme

To import an authorization schema(roles/privileges) please set the `trampoline.security.authorization.schema.run.configuration` property to `true`

Roles and privileges can be configured in Java by implementing the `AuthorizationSchemeConfiguration`.
A NOP implementation is loaded by default and ran when the `ContextRefreshedEvent` event is fired(after application load).
It is recommended to only do this in the development profile, export the SQL and import those into the production db.
It will probably cause conflicts if you try to add roles that already exist.

### Logging user activity

Please make sure you have the [trampoline-security-logging-starter](../trampoline-security-logging-starter) if you wish to add `User` activity logging.
To enable logging add the `@EnableUserActivityLogging` annotation.

To log an activity add the `@LogUserActivity` above a method and specify the identifier, category and activity layer.

If you wish to handle all activities that are generated please implement the `UserActivityHandler` interface.

By default all activity is summarized and logged to the console.