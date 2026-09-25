# AndroidAppLockscreen

An easily implementable, clean, minimal lockscreen library for Android apps — PIN entry, set/change/disable password, custom background colour. Design inspired by [Diaro](https://play.google.com/store/apps/details?id=com.pixelcrater.Diaro).

Image1                     |  Image2
:-------------------------:|:-------------------------:
![screenshot_1532237234](https://user-images.githubusercontent.com/6418354/43042536-7e991234-8da2-11e8-8d17-f15c963a6ad2.png)  | ![screenshot_1532237239](https://user-images.githubusercontent.com/6418354/43042543-a5326274-8da2-11e8-8a96-31e84eef0c98.png)

## Installation

Add JitPack to your root `build.gradle`:

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Add the dependency:

```gradle
dependencies {
    implementation 'com.github.p32929:AndroidAppLockscreen:1.2'
}
```

## Usage

Two steps:

1. Extend all your `Activity` classes from `LockscreenHandler`
2. Call `EasyLock.checkPassword(this);` in the `onCreate` of whichever activity starts first

```java
public class MainActivity extends LockscreenHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyLock.checkPassword(this);
    }
}
```

## API

| Call | What it does |
|---|---|
| `EasyLock.setPassword()` | Set a new password |
| `EasyLock.checkPassword()` | Show the lockscreen and verify |
| `EasyLock.changePassword()` | Change the existing password |
| `EasyLock.disablePassword()` | Turn the lock off |
| `EasyLock.setBackgroundColor()` | Set the lockscreen background colour |
| `EasyLock.forgotPassword(new View.OnClickListener())` | Handle taps on "Forgot password" |

Check out the `app` module in this repository for a full working example :)
Enjoy...

## Support

If you like my works and want to support me/my works, feel free to support or donate. My payment details can be found here: https://p32929.github.io/SendMoney2MeV1/

## License

MIT License — Copyright (c) 2018 Fayaz Bin Salam. See [LICENSE](LICENSE) for the full text.

## Contributing

Contributions are warmly welcomed and greatly appreciated! Whether it's a bug fix, new feature, or improvement, your input helps make this project better for everyone.

Before submitting a pull request, please:

1. Create an issue describing the feature or bug fix you'd like to work on
2. Wait for discussion and approval to ensure alignment with project goals
3. Fork the repository and create your feature branch
4. Submit your pull request with a clear description of changes

This approach helps avoid duplicate efforts and ensures smooth collaboration. Thank you for considering contributing!

## Share

Sharing this repository with your friends is just one click away from here

[![facebook](https://user-images.githubusercontent.com/6418354/179013321-ac1d1452-0689-493f-9066-940cf2302b6e.png)](https://www.facebook.com/sharer/sharer.php?u=https://github.com/p32929/AndroidAppLockscreen/)
[![twitter](https://user-images.githubusercontent.com/6418354/179013351-7d8d6d1c-4ce2-46ab-bef8-4c4765a1b888.png)](https://twitter.com/intent/tweet?url=https://github.com/p32929/AndroidAppLockscreen/)
[![tumblr](https://user-images.githubusercontent.com/6418354/179013343-3111f55a-3b90-40c7-8487-9777348672b0.png)](https://www.tumblr.com/share?v=3&u=https://github.com/p32929/AndroidAppLockscreen/)
[![pocket](https://user-images.githubusercontent.com/6418354/179013334-b095c45f-becf-49f4-9ee1-5a731a9b1f85.png)](https://getpocket.com/save?url=https://github.com/p32929/AndroidAppLockscreen/)
[![pinterest](https://user-images.githubusercontent.com/6418354/179013331-44cd9206-11b1-4b65-becb-5863b61c828f.png)](https://pinterest.com/pin/create/button/?url=https://github.com/p32929/AndroidAppLockscreen/)
[![reddit](https://user-images.githubusercontent.com/6418354/179013338-7416ae3f-73ba-4522-86e1-1374d7082d22.png)](https://www.reddit.com/submit?url=https://github.com/p32929/AndroidAppLockscreen/)
[![linkedin](https://user-images.githubusercontent.com/6418354/179013327-ca7b7102-1da8-4b1c-858f-1a6e5f21bd70.png)](https://www.linkedin.com/shareArticle?mini=true&url=https://github.com/p32929/AndroidAppLockscreen/)
[![whatsapp](https://user-images.githubusercontent.com/6418354/179013353-f477fa0b-3e6f-4138-a357-c9991b23ff88.png)](https://api.whatsapp.com/send?text=https://github.com/p32929/AndroidAppLockscreen/)

---

## Support

If this saved you time, you can buy me a coffee — it keeps these projects maintained and free.

[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20me%20a%20coffee-%E2%98%95-FFDD00?style=for-the-badge&logo=buymeacoffee&logoColor=black)](https://www.buymeacoffee.com/p32929)

**Sponsor a mention** — $499 one-time: your name + link in this section for 3 months. **$1,200 one-time:** featured placement at the top of this section, plus a pinned mention on [my X profile](https://x.com/p32929), for 3 months. Email **[fayazdevinbox@uberip.com](mailto:fayazdevinbox@uberip.com)** for an invoice.

<!-- hire-block -->

---

## 💼 Using this at a company?

I do fixed-price delivery work on my own projects. One invoice, one date, no hourly billing:

| | |
|---|---|
| **White-label build** — this project rebranded, extended and deployed as yours | **$6,500** · 3 weeks |
| **Custom app from scratch** on my own stack, signed and auto-updating | **$12,500** · 6 weeks |
| **Production-hardening sprint** — 72 hours on this project, for your load and your security review | **$999** |
| **Ongoing capacity** — one project-week of my time reserved every month | **$9,000 / month** |

Full details → **[p32929.github.io/hire](https://p32929.github.io/hire/)** · Email **[fayazdevinbox@uberip.com](mailto:fayazdevinbox@uberip.com)** — scoping and quotes are free and I answer within one business day.
