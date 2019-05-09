# example-app

Spring Boot example application utilising Trampoline to create a simple REST blogging API.

## Getting started

Please clone this project locally and run the `ExampleAppApplication` in your app. Or use `./mvnw spring-boot:run`

## Dummy users

The following dummy users are generated when the application finished loading(using a ContextRefreshed event listener):

- user()ROLE_USER): password: test, email: user@example.com
- joe(ROLE_USER): password: test, email: joe@example.com
- admin(ROLE_USER, ROLE_ADMIN): password: test, email: admin@example.com

## REST mappings

For this example we require the user to always be authenticated(except for signup, authorize and unprotected ping routes).
All other requests should contains the following header containing the JWT token obtained from the authorize route: `Authorization: Bearer <JWT TOKEN HERE>`


To make a route publicly accessible ignore it using the WebSecurity configuration in Spring(see the com.lucadev.example.trampoline.configuration package).

| Route                             | Method        | Description                           |
|-----------------------------------|:-------------:|--------------------------------------:|
| /whoami                           | GET           | Summary of the authenticated user     |
| /ping/unprotected                 | GET           | Unprotected ping returning a pong     |
| /ping/protected                   | GET           | Protected ping returning a pong       |
| /signup                           | POST          | Register new user                     |
| /auth/authorize                   | POST          | Authorize and obtain JWT token        |
| /blogs                            | GET           | Return all blogs                      |
| /blogs                            | POST          | Submit a new blogpost                 |
| /blogs/{id}                       | GET           | View blogpost                         |
| /blogs/{id}                       | DELETE        | Delete blogpost                       |
| /blogs/{id}                       | PATCH         | Edit blogpost                         |
| /blogs/{id}/comments              | GET           | View blogpost comments only           |
| /blogs/{id}/comments              | POST          | Add comments to blog post              |
| /blogs/{id}/comments/{id}         | DELETE        | Delete blogpost comments               |
| /blogs/{id}/comments/{id}         | PATCH         | Edit blogpost comments                 |

## PostMan Collection

If you use PostMan to test the API you may import the following collection(2.1): [ExampleApp.postman_collection.json](ExampleApp.postman_collection.json)