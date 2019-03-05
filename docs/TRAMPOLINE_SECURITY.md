# trampoline-security docs
[trampoline-security](../trampoline-security) offers a simple UserDetailsService/UserDetails implementation. The users are persisted through JPA(using our TrampolineEntity from [trampoline-data](../trampoline-data)).

The implementation also adds a simple RBAC implementation through roles and privileges. 
## SystemAuthentication
SystemAuthentication is an Authentication subclass which helps to programmatically import test data in case method security is a problem.
## Architecture
There are three main entities:

1. User: the UserDetails implementation, also a TrampolineEntity
2. Role: a User role
3. Privilege: a privilege, contained in a Role

A user may have zero or more roles. And a role may have zero or more privileges.
 
A privilege may not be duplicate but may be used in multiple roles.

## Properties

* trampoline.security.authentication.emailIdentification: set to true if you only want the user to be able to login through email.

## UserService
User management methods. Also contains methods to handle the UserDetails methods(lock, enable, expire, etc...).
## RoleService
Manage the Role objects.
## PrivilegeService
Manage the Privilege objects.
## AuthorizationSchemeService
This service bean allows you to build the role/privilege scheme through the use of json or using a Java builder.

## AuthorizationSchemeBuilderConfiguration

Create a configuration implementing this interface will allow you to build the authorization scheme using the builder.

### Java builder using a Configuration

Please see the example [BlogExampleAuthorizatrionSchemeBuilderConfiguration](../trampoline-example-app/src/main/java/com/lucadev/example/trampoline/configuration/BlogExampleAuthorizatrionSchemeBuilderConfiguration.java)

Roles and privileges are only added when they do not exist(adding privileges to an existing role using the builder wont work).
This is done since you should have your auth scheme populated on production.