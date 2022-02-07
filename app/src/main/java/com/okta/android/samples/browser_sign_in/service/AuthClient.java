package com.okta.android.samples.browser_sign_in.service;

import android.content.Context;
import android.graphics.Color;

import com.okta.android.samples.browser_sign_in.R;
import com.okta.oidc.OIDCConfig;
import com.okta.oidc.Okta;
import com.okta.oidc.clients.web.WebAuthClient;
import com.okta.oidc.storage.SharedPreferenceStorage;

import java.util.concurrent.Executors;

public class AuthClient {
    private final static String FIRE_FOX = "org.mozilla.firefox";
    private final static String CHROME_BROWSER = "com.android.chrome";

    private static AuthClient INSTANCE = null;
    private static volatile WebAuthClient webAuthClient;

    private AuthClient(Context context) {
        var config = new OIDCConfig.Builder()
                .withJsonFile(context, R.raw.okta_oidc_config)
                .create();

        webAuthClient = new Okta.WebAuthBuilder()
                .withConfig(config)
                .withContext(context)
                .withStorage(new SharedPreferenceStorage(context))
                .withCallbackExecutor(Executors.newSingleThreadExecutor())
                .withTabColor(Color.BLUE)
                .supportedBrowsers(CHROME_BROWSER, FIRE_FOX)
                .setRequireHardwareBackedKeyStore(false) // required for emulators
                .create();
    }

    public static WebAuthClient getWebAuthClient(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AuthClient(context);
        }
        return webAuthClient;
    }
}
