# Getting Started

Prerequisites:
* Maven
* Git 
* Java 8

## Import into your project

Please check the [example-app/pom.xml](../example-app/pom.xml).

As you can see you require the trampoline starter dependency. This dependency adds all of the trampoline starters.

Trampoline is built upon `Spring Boot 2.2.2.RELEASE`

```xml
<dependency>
       <groupId>com.lucadev.trampoline</groupId>
       <artifactId>trampoline-starter</artifactId>
       <version>20190628-SNAPSHOT</version>
</dependency>
```

### trampoline-starter

The `trampoline-starter` dependency wraps all the starter modules into a single dependency.

You may also add the separately or could exclude unwanted dependencies from the starter.

## Enable Trampoline

To enable trampoline services and components in your applications please add the `@EnableTrampoline` annotation. This will scan and load all trampoline components.
This annotation is inside the `trampoline-core` dependency.

Example:

```java
@EnableTrampoline
@SpringBootApplication
public class TrampolineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrampolineApplication.class, args);
    }

}
```

## Enable user logging

If you wish to use [trampoline-user-logging](../trampoline-user-logging) you must also add the `@EnableUserActivityLogging` annotation on your class like:

```java
@EnableTrampoline
@EnableUserActivityLogging
@SpringBootApplication
public class TrampolineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrampolineApplication.class, args);
    }

}
```

## Enable security chain ignore annotation

If you wish to use `@IgnoreSecurity` from [trampoline-security-web](../trampoline-security-web) to disable security on a mapping add the `@EnableIgnoreSecurity` annotation.

```java
@EnableTrampoline
@EnableIgnoreSecurity
@SpringBootApplication
public class TrampolineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrampolineApplication.class, args);
    }

}
```

When `@IgnoreSecurity` is added on a request mapping it will disable the entire security chain for that route.

## Enable abac annotations

If you wish to use `@PrePolicy` or `@PostPolicy` from [trampoline-security-abac](../trampoline-security-abac) to secure methods using abac please use the `@EnablePrePostPolicy` annotation.

```java
@EnableTrampoline
@EnablePrePostPolicy
@SpringBootApplication
public class TrampolineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrampolineApplication.class, args);
    }

}
```

You can then apply abac policies on methods using `@PrePolicy` or `@PostPolicy`.

You can always use Spring's `@PreAuthorize` and `@PostAuthorize` with a call to `hasPermission`.
Using `@EnablePrePostPolicy` simply provides a shortcut which requires less code.

## Write your app with the help of Trampoline

For an example on how to use Trampoline in your application please check the [example-app](../example-app) and read the other docs.
