# Trampoline

[![master](http://lucadev.com/service/jenkins/buildStatus/icon?job=trampoline&build=10)](http://lucadev.com/service/jenkins/job/trampoline/10/)
[![next-release](http://lucadev.com/service/jenkins/buildStatus/icon?job=trampoline)](http://lucadev.com/service/jenkins/job/trampoline/)

Collection of reusable Spring Boot starter modules providing multiple new features/and or implementations(Spring Security, etc..).

## Getting Started

Please make sure you have git, maven and Java (min. version 8) installed.

Trampoline is deployed on maven and is built upon `Spring Boot 2.2.2.RELEASE`

Please use the following dependency to use all functionality Trampoline offers:
```xml
<dependency>
       <groupId>com.lucadev.trampoline</groupId>
       <artifactId>trampoline-starter</artifactId>
       <version>20190628</version>
</dependency>
```
Read the [docs](/docs/README.md) for more information.

## Example Project

[example-app](example-app) is a REST api to maintain blogs and comments on blogs.

The readme provides a bunch of information regarding the endpoints, dummy users. I also added a PostMan collection so you can easily test the api through postman.

## Changelog

A changelog can be found under [CHANGELOG.md](CHANGELOG.md).

## Why Trampoline?

Some of you may ask why I wrote trampoline.

I have developed trampoline as a way to easily create monolith REST API's(you really do not require microservices for most projects).
Trampoline has given me the ability to focus more on the actual projects I am hired for.
I can just implement security with a couple of lines and easily add more functionality.

## Versioning

We use the date that development of a new Trampoline version began as a version in the YYYYMMDD format.

Version `20190507` would have started development on the 7th of may 2019.

In-development versions are versioned the same but with the `-SNAPSHOT` suffix.

## Flyway migrations

When Flyway is added to the classpath in your project and you use any of the following: `trampoline-data` and/or `trampoline-asset-store` and/or `trampoline-security`.
You must adhere to some rules when writing your own migrations.

Custom migrations must start with version `V10`. V10 is required since Trampoline reserves atleast 10 migrations.
Either the `db/migration/mysql` or `db/migration/h2` dir is chosen as location for migrations based on the `spring.jpa.properties.hibernate.dialect` property.
When the value of the property contains `mysql` it will use `db/migration/mysql` and when it contains `h2` it will use `db/migration/h2`.
The default fallback is to `db/migration/mysql`.

## Contributing

If you wish to contribute please read the guidelines as described in [CONTRIBUTING.md](/CONTRIBUTING.md)

## Branches

All the changes before releasing a new version(and publishing to maven) will be pushed to the `next-release` branch.
This branch will be merged to `master` before building and deploying to maven.

See [CHANGELOG.md](CHANGELOG.md) for more information.

## Authors

Feel free to contribute to the project.

- [Luca Camphuisen](https://github.com/Camphul)

## License

This project is licensed under the [MIT License](/LICENSE.txt)
