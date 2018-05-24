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
## UserService
User management methods. Also contains methods to handle the UserDetails methods(lock, enable, expire, etc...).
## RoleService
TODO
## PrivilegeService
TODO
## AuthorizationSchemeService
TODO