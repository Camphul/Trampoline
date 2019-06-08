# Trampoline Docs

[Getting started](GETTING_STARTED.md)

## Modules

Please reference the following modules for documentation:

- [trampoline-asset-store](../trampoline-asset-store)
- [trampoline-core](../trampoline-core)
- [trampoline-data](../trampoline-data)
- [trampoline-notify](../trampoline-notify)
- [trampoline-security](../trampoline-security)
- [trampoline-security-abac](../trampoline-security-abac)
- [trampoline-security-jwt](../trampoline-security-jwt)
- [trampoline-security-jwt-web](../trampoline-security-jwt-web)
- [trampoline-security-logging](../trampoline-security-logging)
- [trampoline-web](../trampoline-web)

## Javadoc

- [trampoline-asset-store](https://lucadev.com/trampoline/docs/trampoline-asset-store)
- [trampoline-core](https://lucadev.com/trampoline/docs/trampoline-core)
- [trampoline-data](https://lucadev.com/trampoline/docs/trampoline-data)
- [trampoline-notify](https://lucadev.com/trampoline/docs/trampoline-notify)
- [trampoline-security](https://lucadev.com/trampoline/docs/trampoline-security)
- [trampoline-security-abac](https://lucadev.com/trampoline/docs/trampoline-security-abac)
- [trampoline-security-jwt](https://lucadev.com/trampoline/docs/trampoline-security-jwt)
- [trampoline-security-jwt-web](https://lucadev.com/trampoline/docs/trampoline-security-jwt-web)
- [trampoline-security-logging](https://lucadev.com/trampoline/docs/trampoline-security-logging)
- [trampoline-web](https://lucadev.com/trampoline/docs/trampoline-web)

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
- `trampoline.security.jwt.secret`: jwt signing secret, default is trampolineSecret
- `trampoline.security.jwt.tokenTimeout`: timeout property of jwt, default is 3600
- `trampoline.security.jwt.web.baseMapping`: auth basepath for authorizing and refreshing tokens, default is /auth

We also provide two example properties files:

- [application-dev-example.properties](application-dev-example.properties)
- [application-prod-example.properties](application-prod-example.properties)

## Examples

An example project is available at [example-app](../example-app)