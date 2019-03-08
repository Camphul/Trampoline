# Trampoline Docs

[Getting started](GETTING_STARTED.md)

## Modules

Please reference the following modules for documentation:

- [trampoline-asset-store](../trampoline-asset-store)
- [trampoline-common](../trampoline-common)
- [trampoline-data](../trampoline-data)
- [trampoline-security](../trampoline-security)
- [trampoline-security-abac](../trampoline-security-abac)
- [trampoline-security-jwt](../trampoline-security-jwt)
- [trampoline-security-jwt-web](../trampoline-security-jwt-web)


## Configuration Values

Please make sure you have the following property:

```
spring.main.allow-bean-definition-overriding=true
```

Other properties being used are:

- `trampoline.assetstore.provider`: which AssetStore to use, defaults to local
- `trampoline.assetstore.provider.local.directory`: directory to use for local AssetStore, defaults to ./local-asset-store/
- `trampoline.security.abac.policy.definition.json.filepath`: define which json file to load policy from for trampoline-security-abac
- `trampoline.security.authentication.emailIdentification`: use email instead of username for authorization, defaults to `false`
- `trampoline.security.authorization.schema.run.configuration`: should we run the `AuthorizationSchemaConfiguration`, defaults to `false`
- `trampoline.security.jwt.secret`: jwt signing secret, default is trampolineSecret
- `trampoline.security.jwt.signingAlgorithm`: algorithm used to sign, default is HS512
- `trampoline.security.jwt.tokenHeader`: header containing token, default is Authorization
- `trampoline.security.jwt.tokenHeaderPrefix`: Prefix before jwt, default is Bearer
- `trampoline.security.jwt.tokenTimeout`: timeout property of jwt, default is 3600
- `trampoline.security.jwt.authPath`: auth basepath for authorizing and refreshing tokens, default is /auth

We also provide two example properties files:

- [application-dev-example.properties](application-dev-example.properties)
- [application-prod-example.properties](application-prod-example.properties)

## Examples

An example project is available at [trampoline-example-app](../trampoline-example-app)