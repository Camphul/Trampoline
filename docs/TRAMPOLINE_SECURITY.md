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
This service bean allows you to build the role/privilege scheme through the use of json or using a Java builder.

### Java builder

It is recommended to create a startup listener. We do not have an autoload config ready yet.

Autowire the service and invoke `build()`. This creates a scheme builder. And example of how to use it can be seen here:

```java
//Wrap inside SystemAuthentication SecurityContext
authorizationSchemeService.builder().wrapSystemAuthentication((wrappedBuilder) ->
        //Create user role
    wrappedBuilder.createRole("ROLE_USER").withPrivileges("WHOAMI_GET", "PING").buildAnd()
            //Create admin role, adds privileges from ROLE_USER to this role.
            .createRole("ROLE_ADMIN").withExistingRolePrivileges("ROLE_USER").withPrivileges("MANAGE_USERS").buildAnd()
            //Only build when dev profile is enabled
            .forProfile("dev", (devBuilder) ->
                //Add developer role
                devBuilder.createRole("ROLE_DEVELOPER").withPrivilege("DEVELOPER_ACCESS").buildAnd()
            )
);
```