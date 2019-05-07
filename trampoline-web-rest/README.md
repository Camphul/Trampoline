# trampoline-web-rest

Web components such as REST exception handling, data validation, etc...

Credits to: https://github.com/jirutka/spring-rest-exception-handler/ as it helped me implement it myself.

## Architecture 

trampoline-web-rest aims to improve the experience of writing REST API's in Spring.

The following features are currently implemented:

- Validation through `@Valid` will throw a 400 bad request together with details on the invalid data.
- `RestExceptionHandler` can be implemented to handle an exception and still return something to the client(similar to `@ControllerAdvice`)
- `ValidationService` pretty empty at the moment but this will have more validation related methods added later.

To implement `RestExceptionHandler` there's a `RestHandlerExceptionResolver` which has a default implementation loaded by the autoconfiguration.

