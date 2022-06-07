# Okta + Android (Kotlin) & Okta-Hosted Login Page Example

This sample shows how to use the Okta Android SDK to sign a user in to an Android application built in Kotlin. The process uses Authorization Code Flow + PKCE, where the user is redirected to Okta for authentication. After the user authenticates, they are redirected back to the application with an authorization code, which is exchanged for an ID token and access token.

This project requires Android Studio (Arctic Chipmunk 2021.2.1)

This project uses the [okta-mobile-kotlin](https://github.com/okta/okta-mobile-kotlin) SDK.

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

Grab and configure this project using `okta start android-java`.

You can also clone this project from GitHub and run `okta start` in it.

```bash
git clone https://github.com/okta-samples/okta-android-java-sample.git
cd okta-android-java-sample
okta start
```

Follow the instructions printed to the console.

## Run the App

Run the app on emulator or hardware device from Android Studio (**shift+F10** on Linux/Windows |
**Ctrl + R** on macOS)

If you see a home page with a login button, then things are working! Clicking the Login button will redirect you to the Okta sign-in page.

You can sign in with the same account that you created when signing up for your developer org, or you can use a known username and password from your Okta Directory.

> **Note:** For the sake of simplicity everything is done on the main application activity in this sample. In a real application please follow the latest [Android app architecture guide](https://developer.android.com/topic/architecture) and use Fragments and view models.
