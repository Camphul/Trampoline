# trampoline-security-abac

Attribute based access control for Spring Boot using SpeL

Many credits to: https://dzone.com/articles/simple-attribute-based-access-control-with-spring

## Architecture

ABAC allows you to easily implement row-level security. This is partly done using Spring Expression Language(SpEL).
This module expands on trampoline-security by adding the following core components:

- `PolicyRule`: the rule which is applied against the `User`. `PolicyRule` is a `TrampolineEntity` but can also be stored in other formats if required.
- `PolicyContainer`: interface to manage `PolicyRule` objects
- `PolicyEnforcement`: check methods to see if a `User` complies with a `PolicyRule`

A `PolicyRule` consists of the following attributes:

- `name`: short name of the rule
- `description`: describes the rule, to who it applies and what it does
- `target`: SpEL on when to apply this rule.
- `condition`: when to pass assuming the `target` is evaluated to true.

All policy are configured in a json file and persisted to the database(as long as there isn't a rule with the same name already).
To configure a custom path for the json please change the `trampoline.security.abac.policy.definition.json.filepath` property.
The default file being checked is `default-policy.json`


An example of a 1 rule config should look something like this:

```json
{
  "policies": [
    {
      "name": "View WHOAMI page",
      "description": "View GET /whoami",
      "target": "isAction('WHO_AM_I')",
      "condition": "hasAuthority('WHOAMI_GET')"
    }
  ]
}
```

Most of the hard work implementing ABAC is done under the hood by trampoline.
We implement our own `PermissionEvaluator` through a `MethodSecurityConfiguration` and inject our own `SecurityAccessContext`.
This means we add extra methods and variables to the SpEL environment to make it easy for you to write expressions.

We have the following objects/methods available inside SpEL expressions:

- `subject`: the person who triggered the rule to fire
- `resource`: the resource to check against(is the subject the owner of the resource?)
- `action`: the action being taken place(DELETE_BLOG, VIEW_USER, etc.. All defined in the service or controller layer).
- `environment`: in the case of `@PrePolicy` or `@PostPolicy` it contains the method arguments in a map.
- `isAction(action)`: equality method for the action object.

The `hasAuthority` method is built into Spring and will check if a Privilege/Role from trampoline-security is present.

### Pre/PostPolicy annotation

We have implemented our own security annotations which are comparable to `PreAuthorize` and `PostAuthorize`.
These annotations use Spring AoP to check the permissions using the built in `PermissionEvaluator`.

To set a resource for a policy please add the `@PolicyResource` annotation to a parameter.
This works great with `@FindById` inside [trampoline-data-web](../trampoline-data-web)
