# trampoline-common

Common components that are used in nearly all trampoline modules.

## Architecture

This module only contains small components which do not deserve their own modules.
The following components are present:

- Converters: Spring does not come with built in converters to parse parameters into `UUID`. This is essential for multiple modules.
- Models: standard response and request models to keep API's consistent. Think of `MessageResponse`, `SuccessResponse`, etc...
- Services: Minor services like `TimeProvider`. Using `System.currentTimeMillis()` all over your project would be bad.

This module also contains the essential `@EnableTrampoline` annotation required to load all Trampoline components in a project.