# AndroidAppLockscreen
An easily implementable, clean, minimal Lockscreen module for Android Apps

## Installation
Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Add the dependency

```
dependencies {
    implementation 'com.github.p32929:AndroidAppLockscreen:1.0'
}
```

## Usage
Steps to follow:
* Extend all ``Activities`` by ``LockscreenHandler``
* Add ```EasyLock.checkPassword(this);``` in your ```MainActivity``` (or whichever activity starts at first)

After that you can:
* Set password
* Check password
* Change password
* Disable password

## Example:
Step 1: * Extend all ``Activities`` by ``LockscreenHandler`` like this:

```
public class MainActivity extends LockscreenHandler {
   // ............................................
}
```

Step 2: Add ```EasyLock.checkPassword(this);``` in your ```MainActivity``` (or whichever activity starts at first) like this:

```
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyLock.checkPassword(this);
}
```

## Set, Check, Change, Disable Password:
* To set a password, call ```EasyLock.setPassword();```
* To check password, call ```EasyLock.checkPassword();```
* To change password, call ```EasyLock.changePassword();```
* To disable password, call ```EasyLock.disablePassword();```

```setPassword```, ```changePassword``` & ```disablePassword``` needs two parameters:
1st parameter is the ```Context``` & the
2nd parameter is the ```Activity class``` to go after finising setting, changing or disabling password...

Check out the app in the repository for more info :)
Enjoy...

```
MIT License

Copyright (c) 2018 Fayaz Bin Salam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
