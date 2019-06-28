ave t# Changelog

This document contains the broad changes to versions deployed to maven central.

We use the following versioning scheme `YEARMMDD` so version `20190308` would have began development on 8 march 2019.

## 20190509

- Ready to use DTO's for trampoline-security.
- Added trampoline-notify module to handle mails and notifications.
- Refactoring to use ConfigurationProperties.
- Added @IgnoreSecurity to disable security chain on annotated handler(WebSecurity#ignoring() through RequestMappingHandlerMapping).
- Better code quality.
- Added more unit tests.
- abac configuration property key refactors.
- abac json default file is now permissions.json.
- option to specify which policycontainer to use.
- refactor trampoline-security-jwt to trampoline-jwt.
- remove dependency on trampoline-security for trampoline-jwt.
- remove depency on trampoline-security for trampoline-user-logging.
- refactor trampoline-security-logging to trampoline-user-logging.
- migrate most configurations to autoconfig modules.
- you now require `@EnableIgnoreSecurity` to use the `@IgnoreSecurity` annotation.

## 20190507

- Major refactoring of modules.
- trampoline-common -> trampoline-core.
- added trampoline-data-web.
- Modified mechanism for trampoline-user-logging to use SPeL instead of resolvers.
- Updated JWT library.
- Removed useless config properties for jwt.
- Refactored SuccessResponse: use exceptions instead with corresponding statuses.
- Added bunch of Dto's.
- Added MapStruct library support.
- Added DataResponse to wrap response.
- Added ListResponse to wrap list response.
- Rewrite LogUserActivity and the mechanism behind it to make it easier to work with.

## 20190329

- Remove caching functionality from user service to prevent incorrect data.
- Add javadoc site links.
- Refactor UserActivity#principal from String to User.

## 20190308

- First deployment on maven central.