# Changelog

This document contains the broad changes to versions deployed to maven central.

We use the following versioning scheme `YEARMMDD` so version `20190308` would have began development on 8 march 2019.

## 20190509

- Ready to use DTO's for trampoline-security
- Added trampoline-notify module to handle mails and notifications
- Refactoring to use ConfigurationProperties
- Added @IgnoreSecurity to disable security chain on annotated handler
- Better code quality

## 20190507

- Major refactoring of modules
- trampoline-common -> trampoline-core
- added trampoline-data-web
- Modified mechanism for trampoline-security-logging to use SPeL instead of resolvers.
- Updated JWT library
- Removed useless config properties for jwt
- Refactored SuccessResponse: use exceptions instead with corresponding statuses
- Added bunch of Dto's
- Added MapStruct library support
- Added DataResponse to wrap response.
- Added ListResponse to wrap list response.
- Rewrite LogUserActivity and the mechanism behind it to make it easier to work with.

## 20190329

- Remove caching functionality from user service to prevent incorrect data.
- Add javadoc site links
- Refactor UserActivity#principal from String to User.

## 20190308

- First deployment on maven central