# Redirect sign in for Android (Kotlin)

Authenticate a user using the Redirect model in your Kotlin Android app using the Okta Android SDK.

The app launches a sign-in view hosted by Okta that handles the authorization using Authorization Code Flow + PKCE. Control is returned to the app once the user finishes the flow. An authorization code is returned to the app after a successful sign on. This code is then exchanged for ID and access tokens.

Using this project requires:
- Android Studio (Arctic Chipmunk 2021.2.1)
- The [okta-mobile-kotlin](https://github.com/okta/okta-mobile-kotlin) SDK.

# Guide

Follow the [guide](https://developer.okta.com/docs/guides/sign-into-mobile-app-redirect/android/main/) on developer.okta.com.

## Prerequisites

Before running this sample, you will need the following:

- [The Okta CLI Tool](https://github.com/okta/okta-cli#installation)
- An Okta Developer Account (create one using `okta register`, or configure an existing one with `okta login`)
- Android Studio
- A virtual device emulator or a real device connected to work with Android Studio
- Java 11

## Get the Code

Grab and configure this project using `okta start android-kotlin`.

You can also clone this project from GitHub and run `okta start` in it.

```bash
git clone https://github.com/okta-samples/okta-android-kotlin-sample.git
cd okta-android-kotlin-sample
okta start
```

Follow the instructions printed to the console.

## Run the App

Run the app on emulator or hardware device from Android Studio (**shift+F10** on Linux/Windows |
**Ctrl + R** on macOS)

If you see a home page with a login button, then things are working! Clicking the Login button will redirect you to the Okta sign-in page.

You can sign in with the same account that you created when signing up for your developer org, or you can use a known username and password from your Okta Directory.
