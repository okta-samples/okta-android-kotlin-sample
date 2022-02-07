# Okta Android Quick Start

This is the sample project accompanying
the [Okta Android Quick Start](https://developer.okta.com/docs/guides/sign-into-mobile-app/android/main/)

This project was created using Android Studio (Arctic Fox 2020.3.1)

## Prerequisites

To run the project you need the below:

- An Okta Developer Account, you
  can [sign up for a free account](https://developer.okta.com/signup/) or use the Okta CLI as shown
  in next step.
- Android Studio
- A virtual device emulator or a real device connected to work with Android Studio
- Java 11

## Create an Okta app integration via Okta CLI

1. Install the [Okta CLI](https://cli.okta.com/).
1. Run `okta register` to sign up for a new Okta developer account, or if you already have an
   account, run `okta login`.
1. To create the Okta application, run `okta apps create native`. Name the Okta application.
1. You will be presented with the following configuration options:
    1. For **Redirect URI** enter the full redirect URI for your mobile app (for
       example, `com.okta.example:/callback`).
    1. For **Post Logout Redirect URI(s)** enter the full redirect URI for your mobile app (for
       example, `com.okta.example:/`).
1. Note down the client id and issuer URI printed on the console. The okta domain is the domain part
   of the issuer URI

## Build and run the project

Clone the project using below command:

```bash
git clone https://github.com/oktadev/okta-android-quickstart.git
```

Open the project in Android studio. You can run the command `studio` from the cloned directory or
open the directory from Android studio's file menu.

Configure `app/src/main/res/raw/okta_oidc_config.json` with client id and okta domain values from
the Okta App Integration created in previous step

Run the app on emulator or hardware device from Android Studio (**shift+F10** on Linux/Windows |
**Ctrl + R** on macOS)

> **Note:** For the sake of simplicity everything is done on the main application activity in this guide. In a real application please follow the latest [Android app architecture guide](https://developer.android.com/topic/architecture) and use Fragments and view models.