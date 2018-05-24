# trampoline-data docs

[trampoline-data](../trampoline-data) provides some basic extension of Spring Data.

## Base entity
We have a TrampolineEntity which contains an UUID as id and some auditing fields. Simply extend your entity to TrampolineEntity and Trampoline/Spring will do the rest.
### UUID
As entity id we use an UUID(instead of an incrementing integer id)
### Auditing
TrampolineEntity subclasses automatically have JPA auditing enabled. This means you can keep track of the entity creation/last update dates without any extra work.
## Base entity repository
We also have the TrampolineRepository which extends JpaRepository. This repository only requires the entity type(instead of entity type and id type in JpaRepository).

However the entity type must be assignable from the TrampolineEntity.