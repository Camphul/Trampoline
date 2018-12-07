# trampoline-common docs

[trampoline-common](../trampoline-common) is a module that provides functionality that is reused in most other trampoline modules.

## TimeProvider

As the name says. The TimeProvider service bean provides methods to obtain the current time in unix or a Date object.

## UUID Converter

Allows you to put `UUID` as parameter in your controller mapping. Converts a String into the UUID equivalent.

## MessageResponse

A simple model which contains a message. Useful to return string information for your REST controllers.

## SuccessResponse
Extends the MessageResponse by adding a success flag which can be true or false. 