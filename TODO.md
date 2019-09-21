# Trampoline TODO

Development todo list in order of what needs to be completed.


## 1. Apply constructor inject everywhere

Autoconfigs, configs, components, etc.. Apply constructor di.

## 2. Create UML diagram for each module

We want a clear oversight of our wanted architecture and prevent any design issues(such as tight coupling) 


## 3. Refactor trampoline-jwt-security to adapter-like name

The module is basically one big adapter so we should use it in the name.

## 4. Rewrite asset-store module to use Spring Resource

Use Spring's Resource and ResourceLoader class instead of raw file handling.
This will allow implementations to handle more customized assets(such as define which user an asset belongs to using a query param in the Resource URL).

This also integrates better with Spring itself since it's the default way to do things.

Resource's should still be persisted to the DB but only require the URL to be stored.

## 5. Apply adapter and decorator patterns more and better

They can add loads of modularity to Trampoline components. They should also use Spring Ordered interface when possible.
Current implementations of these patterns are lacking.


## 6. data module should configure a Flyway bean

If Flyway is in the classpath Trampoline should provide migrations for the entities it provides such as User, Role, AssetMetaData.

The Flyway bean will be configured through decorators adding locations for migrations.

## 7. notify module to use conditional beans based upon dependent modules. - DONE

Only when the Spring Email library is in the classpath shall the notify module create an EmailService bean.

## 8. split up services for single-responsibility

A ServiceFacade(fat Service) can be created to connect multiple services together.
You could also try to use the Adapter pattern to split up.

## 9. rethink methods to be available in token service

Some can be made easier when looking at the auth mechanism(decode, parse, useless steps, etc..).
JWT signing key should be configurable or just randomly generated aswell.

## 10. Better GDPR compliance

Do more research on database column encryption, write reusable migration code to encrypt or decrypt existing columns etc..

## 11. Refactor code to match design patterns better

Don't just write some code but follow design pattern guidelines. 

## 12. Make ModelMapper mappers conditional

Option to disable the registration mapper beans.
