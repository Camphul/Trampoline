# Getting Started

Prequisites:
* Maven
* Git 
* Java 8

## Build and Install

To get started please run the following commands(assuming you're running linux/osx):

```bash
git clone https://github.com/Camphul/Trampoline.git
cd Trampoline
mvn clean install
```

## Import into your project

Please check the [example pom](../trampoline-example-app/pom.xml).

As you can see you require the trampoline starter dependency. This dependency adds all of the trampoline starters.

```xml
<dependency>
    <groupId>com.lucadev</groupId>
    <artifactId>trampoline-starter</artifactId>
    <version>!!PROJECT VERSION HERE!!</version>
</dependency>
```

### trampoline-starter

The `trampoline-starter` dependency wraps all the starter modules into a single dependency.

You may also add the separately or could exclude unwanted dependencies from the starter.

## Enable Trampoline

To enable trampoline services and components in your applications please add the `@EnableTrampoline` annotation. This will scan and load all trampoline components.
This annotation is inside the `trampoline-common` dependency.

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