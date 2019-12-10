# Trampoline Docs

[Getting started](GETTING_STARTED.md)

## Modules

Please reference the following starter modules for documentation:

- [trampoline-asset-store](../trampoline-asset-store)
- [trampoline-core](../trampoline-core)
- [trampoline-data](../trampoline-data)
- [trampoline-data-web](../trampoline-data-web)
- [trampoline-notify](../trampoline-notify)
- [trampoline-security](../trampoline-security)
- [trampoline-security-abac](../trampoline-security-abac)
- [trampoline-jwt](../trampoline-jwt)
- [trampoline-jwt-security-adapter](../trampoline-jwt-security-adapter)
- [trampoline-user-logging](../trampoline-user-logging)
- [trampoline-web](../trampoline-web)

## Javadoc

- [trampoline-asset-store](https://lucadev.com/trampoline/docs/trampoline-asset-store)
- [trampoline-core](https://lucadev.com/trampoline/docs/trampoline-core)
- [trampoline-data](https://lucadev.com/trampoline/docs/trampoline-data)
- [trampoline-data-web](https://lucadev.com/trampoline/docs/trampoline-data-web)
- [trampoline-jwt](https://lucadev.com/trampoline/docs/trampoline-jwt)
- [trampoline-jwt-security-adapter](https://lucadev.com/trampoline/docs/trampoline-jwt-security-adapter)
- [trampoline-notify](https://lucadev.com/trampoline/docs/trampoline-notify)
- [trampoline-security](https://lucadev.com/trampoline/docs/trampoline-security)
- [trampoline-security-abac](https://lucadev.com/trampoline/docs/trampoline-security-abac)
- [trampoline-user-logging](https://lucadev.com/trampoline/docs/trampoline-user-logging)
- [trampoline-web](https://lucadev.com/trampoline/docs/trampoline-web)

## Configuration Properties

Properties being used are:

- `trampoline.assetstore.provider`: which AssetStore to use, defaults to local
- `trampoline.assetstore.provider.local.directory`: directory to use for local AssetStore, defaults to ./local-asset-store/
- `trampoline.data.gdpr.algorithm`: algorithm used to encrypt personal data. Default: `AES` 
- `trampoline.data.gdpr.cipher`: cipher used to encrypt personal data. Default: `AES/ECB/PKCS5Padding`
- `trampoline.data.gdpr.key`: key used to encrypt personal data(string). Default `MySuperSecretKey`
- `trampoline.security.abac.container.json.file-path`: define which json file to load policy from for trampoline-security-abac
- `trampoline.security.abac.container.jpa.import-from-json`: import rules from json container on startup
- `trampoline.security.abac.container.provider`: define which PolicyContainer to use. Choose between `json`, `jpa`.
- `trampoline.security.jwt.secret`: jwt signing secret, default is trampolineSecret
- `trampoline.security.jwt.header`: Header key containing our auth token.
- `trampoline.security.jwt.header-schema`: Schema prefix inside the header value(Bearer)
- `trampoline.security.jwt.tokenTimeout`: timeout property of jwt, default is 3600
- `trampoline.security.jwt.claims.username`: claim key for username
- `trampoline.security.jwt.claims.authorities`: claim key for authorities
- `trampoline.security.jwt.web.base-mapping`: auth basepath for authorizing and refreshing tokens, default is /auth
- `trampoline.security.jwt.web.authorize-mapping`: auth path to obtain tokens. Will be appended to `trampoline.security.jwt.web.base-mapping`. Defaults to `/authorize`
- `trampoline.security.jwt.web.refresh-mapping`: auth path to refresh existing tokens. Will be appended to `trampoline.security.jwt.web.base-mapping`. Defaults to `/refresh`
- `trampoline.security.jwt.web.user-mapping`: auth path to get full user dto. Will be appended to `trampoline.security.jwt.web.base-mapping`. Defaults to `/user`
- `trampoline.security.allow-email-identification`: allow clients to either authenticate with their username or with their email(only used in trampoline-security)
- `trampoline.notify.email.defaults.from`: address from which the emails are sent.
- `trampoline.notify.email.defaults.template`: default (fallback) template to render.
- `trampoline.notify.email.defaults.subject`: default (fallback) subject for emails. 

We also provide two example properties files:

- [application-dev-example.properties](application-dev-example.properties)
- [application-prod-example.properties](application-prod-example.properties)

All properties have [metadata files](https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-metadata.html) such that you can autocomplete them in your IDE.


## Examples

An example project is available at [example-app](../example-app)
