# trampoline-example-app

Example application using all modules in this project to create a simple REST API for a blogging website.
Everything is documented and I try to provide comments wherever necessary.

This application is written using lombok to generate code. Please install the lombok plugin for your IDE before running.

## Entities

In this application you have the following entities(next to to user/role/privilege):

- BlogPost: author, title, content, comments
- BlogPostComment: author, blogPost, content


## Authorization

We have the following authorization schema:

- ROLE_USER: WHOAMI_GET, PING_PROTECTED
- ROLE_ADMIN: MANAGE_USERS


### ABAC with a taste of RBAC

We do not require privileges to be used since we use attribute based access control(trampoline-security-abac).

It is however highly recommended to use a combination of both(example pseudo-policy: `GIVE ACCESS TO VIEW ALL BLOGS IF USER HAS AUTHORITY(read privilege) BLOGPOSTS_VIEW_ALL`).

We define our policies in a json file. In our case it's: `policy_definition.json` inside the resources directory(same dir as `application.properties`). 

## Dummy users

The following dummy users are generated when the application finished loading(using a ContextRefreshed event listener):

- user()ROLE_USER): password: test, email: user@example.com
- jeff(ROLE_USER): password: test, email: jeff@example.com
- admin(ROLE_USER, ROLE_ADMIN): password: test, email: admin@example.com

## REST routes

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


## Try it out

I have created a collection for Postman for you to try out all the endpoints.
Exported as postman collection 2.1

This collection also contains tests which are ran after the request(chai assets).
Due to this we do not have to write controller tests in Spring but rather run the PostMan collection and check our test results from there.

Might see if there's a Java library to run postman collections and add it to trampoline(or write one myself).

The collection can be found under: [BlogExample.postman_collection.json](BlogExample.postman_collection.json)