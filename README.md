# Trampoline

Trampoline is a collection of Spring Boot starters to develop Spring Boot REST API's even faster.
This is achieved by providing even more opinionated services and standard implementations of Spring Boot starters(security, etc..).

Trampoline still allows you to configure nearly everything by allowing you to override defaults(just like regular Spring Boot starts).
The defaults are based off of my requirements(Camphul) and may not fit your needs.

## Features

Please read the documentation for a full list of features. I'll try to summarize them here:

- Bunch of default response POJO's(`SuccessResponse`, `MessageResponse`, etc..)
- Extension of Spring Data which has a base entity with auditing enabled(date created/updated), a `UUID` as `id`
- Default Spring Security implementation with a `UserDetails` implementation which is persisted through JPA
- JWT security starter which extends `trampoline-security` to add JWT security without requiring you to write any code.
- Attribute based access control using Spring Expression Language to provide row-level security(example: a user may only delete a blogpost if he's the owner of that blogpost)
- Log user activity by adding a single annotation above a method(achieved using Spring AoP). Write your own activity handler or just use the default log output implementation.
- Better exception handling for REST(better response for validation exceptions and whatnot)
- 

## Getting started
Please read the [docs](/docs/README.md) for more information.

## Maven

Trampoline is deployed on maven and is built upon `Spring Boot 2.1.3-RELEASE`

Please use the following dependency to use all functionality Trampoline offers:
```xml
<dependency>
       <groupId>com.lucadev.trampoline</groupId>
       <artifactId>trampoline-starter</artifactId>
       <version>20190329</version>
</dependency>
```

## Example project

[trampoline-example-app](trampoline-example-app) is a REST api to maintain blogs and comments on blogs.

The readme provides a bunch of information regarding the endpoints, dummy users. I also added a PostMan collection so you can easily test the api through postman.

## Changelog and Versioning

A changelog can be found under [CHANGELOG.md](CHANGELOG.md).

## Contributing

If you wish to contribute please read the guidelines as described in [CONTRIBUTING.md](/CONTRIBUTING.md)

## Branches

All the changes before publishing to maven central will be pushed to the `next-release` branch.
This branch will be merged to `master` before building and deploying the maven central.

See [CHANGELOG.md](CHANGELOG.md) for more information.

## License

This project is licensed under the [MIT License](/LICENSE.txt)