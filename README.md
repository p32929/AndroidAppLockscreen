# AndroidAppLockscreen

A passcode lock screen for Android apps. Add it in a few lines, and your app asks for a 4 to 8 digit passcode when it opens and every time people come back to it. People can set, change and turn off the passcode, and unlock with their fingerprint if you allow it.

## Screenshots

Light                                  | Dark
:-------------------------------------:|:-------------------------------------:
![Light](docs/screenshots/light.png)   | ![Dark](docs/screenshots/dark.png)

## What you get

1. **A clean Material 3 lock screen** that follows light and dark mode, and uses the phone's own colours on Android 12 and newer.
2. **Your own look if you want it:** pass your theme with `EasyLock.setTheme()`, or set a background colour.
3. **Smooth feedback:** dots fill in as you type, shake on a wrong passcode, and every key gives a small vibration.
4. **Fingerprint or face unlock,** turned on with one line.
5. **Locks again when people come back** to your app after leaving it.
6. **Passcodes are stored as a salted hash,** not as plain text.
7. **Works from Kotlin and Java,** on Android 5.0 (API 21) and newer.

## Install

Add JitPack to `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

Add the library:

```kotlin
dependencies {
    implementation("com.github.p32929:AndroidAppLockscreen:2.0.0")
}
```

## Usage

1. Extend your activities from `LockscreenHandler`.
2. Call `EasyLock.checkPassword(this)` in `onCreate` of the activity your app opens first.

Kotlin:

```kotlin
class MainActivity : LockscreenHandler() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) EasyLock.checkPassword(this)

        setButton.setOnClickListener { EasyLock.setPassword(this) }
    }
}
```

Java:

```java
public class MainActivity extends LockscreenHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) EasyLock.checkPassword(this);
    }
}
```

## API

| Call | What it does |
|---|---|
| `EasyLock.setPassword(context)` | Asks for a new passcode twice and saves it |
| `EasyLock.checkPassword(context)` | Shows the lock screen, if a passcode is set |
| `EasyLock.changePassword(context)` | Asks for the current passcode, then a new one |
| `EasyLock.disablePassword(context)` | Asks for the current passcode, then turns the lock off |
| `EasyLock.isPasswordSet(context)` | Tells you if a passcode is set |
| `EasyLock.setBiometricEnabled(true)` | Lets people unlock with fingerprint or face |
| `EasyLock.forgotPassword { ... }` | Shows a "Forgot passcode?" button that runs your code |
| `EasyLock.setTheme(R.style.MyTheme)` | Uses your own Material 3 theme on the lock screen |
| `EasyLock.setBackgroundColor(color)` | Sets a background colour |

`setPassword`, `changePassword` and `disablePassword` can also take an activity class to open when they finish, like in 1.x: `EasyLock.setPassword(this, MainActivity::class.java)`. Without it, the lock screen just closes and you are back where you were.

See the `app` module for a full working sample.

## Migrating from 1.x

1. Update the version to `2.0.0`. Your 1.x calls keep working, and saved passcodes still unlock.
2. The library now needs Android 5.0 (API 21) or newer, and a Material 3 or Material Components theme if you pass your own with `setTheme()`.
3. The lock screen uses its theme colours instead of a black background. Call `EasyLock.setBackgroundColor(Color.BLACK)` to keep the old look.
4. `disablePassword()` now asks for the current passcode first, so the lock can't be turned off by anyone holding the phone.
5. Passcodes must be 4 to 8 digits.
6. The `ActivityChanger` interface is gone. It was only used inside the library.
7. Old 1.x versions stay on JitPack, so nothing breaks if you don't update.

## License

MIT License — Copyright (c) 2018 Fayaz Bin Salam. See [LICENSE](LICENSE) for the full text.

## Contributing

Contributions are very welcome, thank you! Before you start on a pull request, please open an issue and tell me what you would like to add or change. That way we can agree on it first, and your time is not wasted on something that might not fit. Thanks for helping make AndroidAppLockscreen better.

## You might also like

<p><a href="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/0"><picture><source media="(prefers-color-scheme: dark)" srcset="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/0-dark.svg"><img src="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/0-light.svg" width="50%" align="top" alt="More apps by p32929 on OpenStore (1)"></picture></a><a href="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/1"><picture><source media="(prefers-color-scheme: dark)" srcset="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/1-dark.svg"><img src="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/1-light.svg" width="50%" align="top" alt="More apps by p32929 on OpenStore (2)"></picture></a><br><a href="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/2"><picture><source media="(prefers-color-scheme: dark)" srcset="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/2-dark.svg"><img src="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/2-light.svg" width="50%" align="top" alt="More apps by p32929 on OpenStore (3)"></picture></a><a href="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/3"><picture><source media="(prefers-color-scheme: dark)" srcset="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/3-dark.svg"><img src="https://openstore.pfay.workers.dev/more/p32929/androidapplockscreen/3-light.svg" width="50%" align="top" alt="More apps by p32929 on OpenStore (4)"></picture></a></p>

<p><a href="https://p32929.github.io/share/?repo=AndroidAppLockscreen"><picture><source media="(prefers-color-scheme: dark)" srcset="https://p32929.github.io/readme/footer-share-dark.svg"><img src="https://p32929.github.io/readme/footer-share-light.svg" width="50%" align="top" alt="Share this project with your friends in one click"></picture></a><a href="https://p32929.github.io/support/"><picture><source media="(prefers-color-scheme: dark)" srcset="https://p32929.github.io/readme/footer-support-dark.svg"><img src="https://p32929.github.io/readme/footer-support-light.svg" width="50%" align="top" alt="If you like my work and want to support me, feel free to support"></picture></a><br><a href="https://openstore.pfay.workers.dev/add"><picture><source media="(prefers-color-scheme: dark)" srcset="https://p32929.github.io/readme/footer-join-dark.svg"><img src="https://p32929.github.io/readme/footer-join-light.svg" width="100%" align="top" alt="Put your own apps on OpenStore: free, your listing lives in your own repo"></picture></a></p>
