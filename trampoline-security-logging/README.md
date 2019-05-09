# trampoline-security-logging

Extend functionality of [trampoline-security](../trampoline-security) by adding the capability to log user activity through the use of Spring AoP.
To enable this feature please add the `@EnableUserActivityLogging` annotation to your application.


## Architecture

trampoline-security-logging works based on the AoP principle(aspect oriented programming).

Use `@LogUserActivity` and write readable descriptions in spel. The arguments are available as variables and more.