# trampoline-web

Web components for Trampoline

## Exceptions

### BusinessRuleViolationException

A `ResponseStatusException` returning a `400 Bad Request` together with a reason.

## Models

There are a bunch of ready-to-use models which can be used to construct DTO's or simply return a value.
These can also be used as request body. These models all implement a `toString` which only returns the wrapped value.
A `IntValueDto` with a value of `4` will return `"4"` when `IntValueDto#toString` is called.

The following models are available:

### BigDecimalValueDto

`BigDecimal` inside a `value` field. Also provides some utility methods for testing positivity/zero/etc..

### BigIntegerValueDto

`BigInteger` inside a `value` field. Also provides some utility methods for testing positivity/zero/etc..

### BooleanValueDto

`boolean` inside a `value` field.

### DataResponse

Wraps an `Object` inside a `data` field.

### DoubleValueDto

Wraps a `double` in a `value` field.

### IntValueDto

Wraps an `int` in a `value` field.

### ListResponse

Wraps a `List` in a `content` field.
Also has `size` and `empty` fields which are computed by the `List` given in the constructor.

### MessageResponse

Wraps a `String` in a `message` field.

### StringValueDto

Wraps a `String` in a `value` field.

### SuccessResponse

Extends `MessageResponse` but the message is always fixed to `ok`.

### UUIDDto

Wraps an `UUID` in an `id` field.

## Converters

Converters convert one type to another type. Trampoline works with a lot of types so Spring converters are essential.

### UUIDConverter

Converts a `String` to `UUID`.
