# [WIP] Android helpers

Convenience library to make Android development more comfortable
(for me at least).

The helpers are singletons (objects in Kotlin) because many times (if not all)
you need to pass the current application context and doing this everytime you
call a function...

So, i decided making singletons.

For example: If you want to show a Message, you first initialize the
SimpleDialogs helper, located in the UIHelpers library, passing the current
application context, and then you can use functions like ShowMessage.


