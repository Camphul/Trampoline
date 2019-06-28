# trampoline-security-web

Web components to integrate [trampoline-security](../trampoline-security) with the web.

## Architecture

We have a couple of features:

- Models and mappers(User, Role, Privilege)
- Security utilities

## Ignoring the security chain using an annotation

Before trampoline you had to create a web security configuration and manually ignore the security chain for a mapping.
You can now ignore the security chain for requestmappings using the `@IgnoreSecurity` annotation on your handler method.
