# example-app

Spring Boot example application utilising Trampoline to create a simple REST blogging API.

## Getting started

Please clone this project locally and run the `ExampleAppApplication` in your app. Or use `./mvnw spring-boot:run`

## Dummy users

The following dummy users are generated when the application finished loading(using a ContextRefreshed event listener):

- user()ROLE_USER): password: test, email: user@example.com
- joe(ROLE_USER): password: test, email: joe@example.com
- admin(ROLE_USER, ROLE_ADMIN): password: test, email: admin@example.com

There are also dummy users imported when flyway is present.

## Flyway

Custom migrations must start with version `V10` and be inside `db/migration/mysql` or `db/migration/h2`.
V10 is required since Trampoline reserves atleast 10 migrations.

## REST mappings

For this example we require the user to always be authenticated(except for signup, authorize and unprotected ping routes).
All other requests should contains the following header containing the JWT token obtained from the authorize route: `Authorization: Bearer <JWT TOKEN HERE>`


To make a route publicly accessible ignore it using the WebSecurity configuration in Spring(see the com.lucadev.example.trampoline.configuration package).

| Route                             | Method        | Description                           |
|-----------------------------------|:-------------:|--------------------------------------:|
| /whoami                           | GET           | Summary of the authenticated user     |
| /ping                             | GET           | Protected ping returning a pong       |
| /auth/authorize                   | POST          | Authorize and obtain JWT token        |
| /blogs                            | GET           | Return all blogs                      |
| /blogs                            | POST          | Submit a new blogpost                 |
| /blogs/{id}                       | GET           | View blogpost                         |
| /blogs/{id}                       | DELETE        | Delete blogpost                       |
| /blogs/{id}/comments              | GET           | View blogpost comments only           |
| /blogs/{id}/comments              | POST          | Add comments to blog post              |
| /blogs/{id}/comments/{id}         | DELETE        | Delete blogpost comments               |

## PostMan Collection

If you use PostMan to test the API you may import the following collection(2.1): [ExampleApp.postman_collection.json](ExampleApp.postman_collection.json)

## Script to drop db

```mysql
DROP FUNCTION IF EXISTS t_generate_uuid_bin;
DROP FUNCTION IF EXISTS t_bin_to_uuid;
DROP FUNCTION IF EXISTS t_uuid_to_bin;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `blogpost`, `blogpost_comment`, `bind_blogpost_blogpost_comment`, `flyway_schema_history`, `user_document`, `trampoline_asset_meta_data`, `trampoline_policy_rule`, `trampoline_privilege`, `trampoline_role`, `bind_role_privilege`, `trampoline_user`, `bind_user_role`;
SET FOREIGN_KEY_CHECKS = 1;
```
