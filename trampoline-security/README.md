# trampoline-security

Module that expands upon some basic Spring Security functionality. 

## User layout

We have three entities which are all persisted using JPA:

- User
- Role
- Privilege

A User can have multiple roles and a role consists out of one or more privileges.
These roles/privileges will be used for fine-grained access controll in another module.