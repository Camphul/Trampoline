# trampoline-security-logging

This module is completely decoupled from [trampoline-security](../trampoline-security) and can be used on existing spring security implementations.
You however can always cast the `UserDetails` to `User`.

Easily add the capability to log user activity through the use of Spring AoP.
To enable this feature please add the `@EnableUserActivityLogging` annotation to your application.


## Architecture

trampoline-security-logging works based on the AoP principle(aspect oriented programming).

Use `@LogUserActivity` and write readable descriptions in spel. The arguments are available as variables and more.