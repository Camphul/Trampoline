# trampoline-common docs

[trampoline-common](../trampoline-common) is a module that provides functionality that is reused in most other trampoline modules.

## TimeProvider

As the name says. The TimeProvider service bean provides methods to obtain the current time in unix or a Date object.

## UUID Converter

The UUIDConverter handles internal UUID conversion. This way you can easily handle UUID path variables inside of controllers(you don't have to convert from String each time).

## MessageResponse

A simple model which contains a message. Useful to return string information for your REST controllers.

## SuccessResponse
Extends the MessageResponse by adding a success flag which can be true or false. 