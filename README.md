# Trampoline

Jump straight into serious Spring development through the use of multiple ready-to-use Spring Boot starters.

## About

Trampoline is a collection of Spring Boot 2 starters to speed up development by providing even more opinionated services.
However, a lot of services/components maintain configurable through the use of auto-configurations.

## Features

Please read the documentation for a full list of features. I'll try to summarize them here:

- Bunch of common components: `TimeProvider` to get current datetime, `UUIDConverter` converts controller parameters automatically to `UUID`, bunch of models to keep your REST responses consistent.
- Spring Data extension adding a base entity. JPA auditing(created, last updated at) and a `UUID` as `id`(you wont have to add an `id` column yourself).
- Binary file upload/download. Don't store uploads as blobs but store them using a `AssetStore`. Only references to where the file is really stored is stored in the DB. Allows you to use S3, GCP. local fs.
- Spring Security implementation which persists to a datasource using JPA.
- JWT authorization which is achieved by implementing existing Spring Security interfaces. Meaning you can switch to JWT with only a couple lines of code!
- Attribute based access control: achieve row-level security on resource by writing Spring Expression Language(SpEL). 

## Getting started
Please read the [docs](/docs/README.md) for more information.

## Maven

Trampoline is deployed on maven and is built upon `Spring Boot 2.1.3-RELEASE`

Please use the following dependency to use all functionality Trampoline offers:
```xml
<dependency>
       <groupId>com.lucadev.trampoline</groupId>
       <artifactId>trampoline-starter</artifactId>
       <version>20190308</version>
</dependency>
```

## Example project

[trampoline-example-app](trampoline-example-app) is a REST api to maintain blogs and comments on blogs.

The readme provides a bunch of information regarding the endpoints, dummy users. I also added a PostMan collection so you can easily test the api through postman.


## Contributing

If you wish to contribute please read the guidelines as described in [CONTRIBUTING.md](/CONTRIBUTING.md)

## License

This project is licensed under the [MIT License](/LICENSE.txt)