# trampoline-data

This module extends functionality that is found in Spring Data JPA by using a base entity and enabling features like JPA auditing.
Pagination is also a common issue regarding project so there's a bunch of Pagination related classes.

## Architecture

At the core of trampoline-data we have `TrampolineEntity` which acts as a base entity for JPA.
To manage `TrampolineEntity` it's recommended to implement the `TrampolineRepository` interface(which extends `JpaRepository`).

Another common exception I was missing is one when a resource(entity) could not be found.
This is why there's a `ResourceNotFoundException`. This is a `RuntimeException` which will throw a 404 to the client.

To remap a `Page` object we have created the `MappedPage#of` method. This accepts a `Page` object and a mapping lambda and will return a `MappedPage`.

## MappedPage

If you do not want JPA entities inside of a `Page` to directly to return to the client you can use `MappedPage`.
This gives you the ability to easily map the entities inside to their DTO's.

Example:

```java
@GetMapping
public Page<BlogPostSummaryDto> findAll(Pageable pageable) {
    return MappedPage.of(this.blogPostService.findAll(pageable),
            this.blogPostMapper::toSummaryDto);
}
```

## GDPR Compliance

Trampoline Data offers basic GDPR compliance functionality to encrypt personal data.
To enable GDPR compliance add the `@EnableGdprCompliance` annotation to your configuration.

The way this module offers compliance is by adding a `@PersonalData` annotation.
This annotation can be added to entity fields of type `String` like the following:

```java
public class User extends TrampolineEntity implements UserDetails {

	@PersonalData
	@Column(name = "username", nullable = false, unique = true, updatable = false)
	private String username;

	@PersonalData
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	//etc etc..
}
```

Trampoline will then register Hibernate event listeners to encrypt and decrypt entities when loaded/persisted.

To maintain working repository queries you are required to also add the `@PersonalData` annotation to method parameters.
This can be done as such:

```java
@Repository
public interface UserRepository extends TrampolineRepository<User> {

	Optional<User> findOneByUsername(@PersonalData String username);

	Optional<User> findOneByEmail(@PersonalData String email);

	@Query("from User u where u.username = :identifier or u.email = :identifier")
	Optional<User> findOneByUsernameOrEmail(@PersonalData String identifier);

}
```

Trampoline will intercept the method call to the repository method, check for the annotation and encrypt the argument before invoking the method.
As you can see this also works well with methods annotated with `@Query`.

The only downside to this is that queries such as `sortByUsername` will not work(since it will sort on the base64 encoded encrypted string).
This also disables any `LIKE` queries.

### Encrypting other datatypes

If you wish to use the same encryption for other data types it is recommended to write your own `AttributeConverter`.
As long as `@EnableGdprCompliance` is enabled you can use

### Configuring ciphers

We have the following configuration properties:

- `trampoline.data.gdpr.key`: string key
- `trampoline.data.gdpr.cipher`: cipher used
- `trampoline.data.gdpr.algorithm` algorithm used

## Schema conventions

Please follow the following schema conventions:

- lowercase table/fieldnames with a `_` as separator(for spaces).
- table names in single verb(trampoline_user instead of trampoline_users)
- apply correct constraints
- Foreign key constraints following the following format: `fk_[referencing table name]_[referencing field name]_[referenced table name]_[referenced field name]`
- Unique constraints following this format: `uc_[referencing table name]_[referencing field name]_[referenced field name]`
- Unique constraint may also be named `uc_[referencing table name]` when used in a binding table.
- Tables used for many/many relationships following this name: `bind_[referencing (parent) table name]_[referenced (child) table name]`
- And please do not overuse quotes.
- You may skip the `trampoline_` or whatever prefix you use on constraint names.
- Binding tables use this for fk: `fkb_[referencing field name]_[second table name which is bound]`
- Index names follow this convention: `idx_[table name]_[field name]`
