# trampoline-security

Module that expands upon some basic Spring Security functionality together with trampoline-data to store identities.

## Architecture

trampoline-security is the base module to implement security in your trampoline based application.

This module is probably the most opinionated as the User class is a TrampolineEntity and implements Spring's UserDetails.
Each User can have 0 or more roles assigned(in the form of a Role). Each role may contain 0 or more privileges(in the form of a Privilege).

There's service's for each component:

- UserService: extends Spring's UserDetailsService
- RoleService: manages roles
- PrivilegeService: manages privileges
- UserPasswordService: separate service dedicated to manage user's passwords(through the use of Spring's PasswordEncoder).

Roles and privileges can be configured in Java by implementing the AuthorizationSchemeBuilderConfiguration.