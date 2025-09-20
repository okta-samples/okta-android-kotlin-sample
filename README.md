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

* [Java 17+](https://sdkman.io/jdks)
* An Okta Integrator Free Plan account. To get one, sign up for an [Integrator account](https://developer.okta.com/login). Once you have an account, sign in to your [Integrator account](https://developer.okta.com/login). Next, in the Admin Console:

1. Go to **Applications > Applications**
2. Click **Create App Integration**
3. Select **OIDC - OpenID Connect** as the sign-in method
4. Select **Web Application** as the application type, then click **Next**
5. Enter an app integration name, e.g. `My Android Kotlin Sample App`
6. Configure the redirect URIs:
- For the redirect URIs you will use the "reverse domain" of your Okta organization URL.
- E.g. if your org URL is `https://dev-13333337.okta.com` your reverse domain is `com.okta.dev-13333337`. 
- **Sign-in redirect URIs:** `com.okta.dev-13333337:/callback`
- **Sign-out redirect URIs:** `com.okta.dev-13333337:/logout`
7. In the **Controlled access** section, select the appropriate access level
8. Click **Save**

Creating an OIDC Web App manually in the Admin Console configures your Okta Org with the application settings. You may also need to configure trusted origins for `com.okta.dev-13333337` in **Security > API > Trusted Origins**.

## Get the Code

```bash
git clone https://github.com/okta-samples/okta-android-kotlin-sample.git
cd okta-android-kotlin-sample
```

Update your config file at `okta.properties` with the values from your application's configuration:

```properties
discoveryUrl=https://dev-13333337.okta.com/oauth2/default/.well-known/openid-configuration
clientId=0oab8eb55Kb9jdMIr5d6
signInRedirectUri=com.okta.dev-13333337:/callback
signOutRedirectUri=com.okta.dev-13333337:/logout
```

You can also update the example config file at `.okta/sample-config.yaml`:

```yaml
oauthClient:
  redirectUris:
    - com.okta.dev-13333337:/callback
  postLogoutRedirectUris:
    - com.okta.dev-13333337:/logout
  applicationType: native

```

### Where are my new app's credentials?

After creating the app, you can find the configuration details on the app’s **General** tab:
- **Client ID:** Found in the **Client Credentials** section
- **Org URI:** Found in the **Issuer URI** field for the authorization server that appears by selecting **Security > API** from the navigation pane.

## Enable Refresh Token

Manually enable Refresh Token on your Okta application to avoid third-party cookies. Sign in to your Okta Developer Edition account. Press the **Admin Console** button to navigate to the Okta Admin Console. In the sidenav, navigate to **Applications** > **Applications** and find the Okta application for this project named `okta-android-kotlin-sample`. Edit the application's **General Setting** to enable the **Refresh Token** checkbox. **Save** your changes.

## Run the App

Run the app on emulator or hardware device from Android Studio (**shift+F10** on Linux/Windows |
**Ctrl + R** on macOS)

If you see a home page with a login button, then things are working! Clicking the Login button will redirect you to the Okta sign-in page.

You can sign in with the same account that you created when signing up for your developer org, or you can use a known username and password from your Okta Directory.
