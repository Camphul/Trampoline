# Contributing

Thank you for considering to contribute to Trampoline.
Please read the below requirements to make sure you contribute to the project correctly.

## Issue/Feature Request

Please follow the templates to make a bug report or feature request.

## Code Style

Please install the checkstyle plugin for your IDE and import the [spring-javaformat-checkstyle](https://github.com/spring-io/spring-javaformat/tree/master/src/checkstyle).

All code must pass with the following checks being an exception:

- RegexpHeaderCheck
- ImportOrderCheck
- AvoidStarImportCheck
- NewLineAtEndOfFileCheck

## Code Formatting

The format of your code should match the format as defined in [spring-javaformat](https://github.com/spring-io/spring-javaformat).
Trampoline already has the `spring-javaformat` plugin in the pom which requires your code to be formatted correctly.

Run `./mvnw spring-javaformat:apply` to automatically format your code according to the set standard.

## JavaDoc

Provide basic JavaDoc for your code. The JavaDoc must follow the checkstyle guidelines.
Some basic guidelines are:

- First sentence must always end with a dot.
- All params are present if applicable.
- @throws and @returns are present if applicable.
- Both interface and implementation must be documented.

## Other Requirements

### Tests

You are not forced to have a 100% test coverage but please consider writing tests using JUnit and Mockito.

### Non-code files

If you work on a module please make sure the following files are present at the module root:

- LICENSE.txt
- README.md

## Building with maven

To build trampoline use the following command:
```
./mvnw clean install -Dgpg.skip
```

## Pull Requests

Before creating a pull request run the checkstyle plugin again and build the project to make sure it succeeds.