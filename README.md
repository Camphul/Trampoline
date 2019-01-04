# Trampoline
Jump straight into serious Spring development through the use of multiple ready-to-use Spring Boot starters.

Please check [trampoline-example-app](/trampoline-example-app) for an example application.

## About

Trampoline is a collection of Spring(boot) starters to speed up development by providing even more opinionated service.
However, a lot of services/components maintain configurable.

## Features

* User management(persisted through JPA, see trampoline-security)
* JPA base entity to handle JPA auditing, provides an UUID id, date create, and date updated.
* ABAC: extension of the user management starter. Configure policies using SpeL expressions, load them from JSON or persist them using JPA(can be imported from JSON too!).
Only the policy configurations are required to get started.
* RBAC: Simple RBAC implementation with hierarchy. With roles and privileges(authorities).
* JWT starter: import the JWT starter and have JWT enabled without any required configurations(expands on the user management)
* Asset storage: handle file uploads with ease without having to persist everything in the db as a blob.
* Asset storage decoupling: write your own AssetStore implementation to work with S3, GCP, Azure or your local filesystem.

## Getting started
Please read the [docs](/docs/README.md) for more information.

## Example project

[trampoline-example-app](trampoline-example-app) is a REST api to maintain blogs and comments on blogs.

The readme provides a bunch of information regarding the endpoints, dummy users. I also added a PostMan collection so you can easily test the api through postman.

## Project Status

This project is still in early development. Please do not use it for any serious applications(yet).


## Roadmap

These items are not in a specific order:
- ModelMapper/DTO simplification: prevent the creation of too many models(will look into the existing Spring solutions)
- OAuth2 support for trampoline-security
- Something to make frontend easier too(prewritten VueJS components/services? Or template rendering things?)
- Something like Spring REST but with ABAC capability and no HATEOAS mess?
- Improve AssetStore and write implementations for cloud services?
- Something something message queue/kafka related??
- NoSQL/Document db support?
- Atleast 70% test coverage ehhh?
- Better abstractions.
- Bunch of things to prevent duplicate code in controllers?
- Fix websecurity route config in JWT lib and move to JWT-WEB lib(possible security leak?)

## Contributing

If you wish to contribute please read the guidelines as described in [CONTRIBUTING.md](/CONTRIBUTING.md)

## License

This project is licensed under the [MIT License](/LICENSE.txt)