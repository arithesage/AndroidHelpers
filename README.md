# [WIP] Android library base project
A minimal base for starting an Android library without needing Android Studio.

Why not just use Android Studio?
Well... because i don't like being tied to a development environment if
i can avoid it.

You can import the project, once initialized, in Android Studio if you wish.


1) First, clone/download the repo and rename the root folder with the name
of your project (if want to work with Java instead of Kotlin, clone the java
branch instead of master).

2) Execute init.py with your project's full package name as argument.

Example:

- git clone [-b java] https://arithesage/AndroidLibraryBase.git MyProject --depth=1
  (append the '-b java' part if you want to work with Java instead of Kotlin)

- cd MyProject
- chmod -R 770 . (just for make sure the dir tree has the correct permissions)
- ./init.py me.android.myproject

Now, the project should be ready for working with it.


Also, you can run add_module.py and add_native_module.py in order to create
additional modules for your library, again without needing using Android Studio.

Just note that both scripts require TWO arguments instead one:
module name and package.

So, instead doing:
./init.py me.android.modules.mymodule

we do:
./add_module.py mymodule me.android.modules.mymodule

