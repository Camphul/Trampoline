# Roadmap

This is the development roadmap for release 20190507.

## Refactors

- pass environment to policyenforcer with method params.

## Code styling

https://github.com/spring-io/spring-javaformat 


### Package naming

The maven module has the base package of `com.lucadev.trampoline.{MODULENAME}`

If you use Spring Data you have the `persistence` package with two subpackages called `entity` and `repository`.
If you have enums in your entities places them in the base `persistence` package.

All package names are single verb.

Default interface implementations are called Trampoline{INTERFACENAME} 
Interface implementations are placed in the same package.
If you require to implement multiple interfaces for default implementation create a `impl` package or the name of the implementation.

Use constructor injection.

## Done

- redo jwt mechanism
- trampoline-core will be refactored to trampoline-core
- models and web related services in trampoline-core will be moved to trampoline-web-rest
- trampoline-security-logging needs to be better(the architecture is not really scalable atm, maybe use SPeL for formatting messages).

