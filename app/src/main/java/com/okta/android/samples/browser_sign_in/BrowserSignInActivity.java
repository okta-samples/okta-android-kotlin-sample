package com.okta.android.samples.browser_sign_in;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.okta.android.samples.browser_sign_in.service.AuthClient;
import com.okta.oidc.AuthorizationStatus;
import com.okta.oidc.RequestCallback;
import com.okta.oidc.ResultCallback;
import com.okta.oidc.Tokens;
import com.okta.oidc.clients.sessions.SessionClient;
import com.okta.oidc.clients.web.WebAuthClient;
import com.okta.oidc.net.response.UserInfo;
import com.okta.oidc.util.AuthorizationException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class BrowserSignInActivity extends AppCompatActivity {
    private static final Logger logger = Logger.getLogger("BrowserSignInActivity");
    private WebAuthClient client;
    private SessionClient sessionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_sign_in);

        findViewById(R.id.browser_sign_in_btn).setOnClickListener(v -> client.signIn(this, null));
        findViewById(R.id.logout_btn).setOnClickListener(v -> client.signOutOfOkta(this));

        client = AuthClient.getWebAuthClient(this);
        sessionClient = client.getSessionClient();

        client.registerCallback(new ResultCallback<>() {
            @Override
            public void onSuccess(@NonNull AuthorizationStatus status) {
                if (status == AuthorizationStatus.AUTHORIZED) {
                    // client is authorized.
                    logger.info("Auth success");
                    runOnUiThread(() -> {
                        findViewById(R.id.browser_sign_in_btn).setVisibility(View.GONE);
                        findViewById(R.id.logout_btn).setVisibility(View.VISIBLE);
                        showUserInfo();
                    });

                } else if (status == AuthorizationStatus.SIGNED_OUT) {
                    // this only clears the browser session.
                    logger.info("Sign out success");
                    runOnUiThread(() -> {
                        findViewById(R.id.browser_sign_in_btn).setVisibility(View.VISIBLE);
                        findViewById(R.id.logout_btn).setVisibility(View.GONE);
                        TextView textView = findViewById(R.id.have_account);
                        textView.setText(getString(R.string.have_account));
                    });
                }
            }

            @Override
            public void onCancel() {
                // authorization canceled
                logger.info("Auth cancelled");
            }

            @Override
            public void onError(String msg, AuthorizationException error) {
                logger.severe(String.format("Error: %s : %s", error.error, error.errorDescription));
            }
        }, this);

        if (sessionClient.isAuthenticated()) {
            logger.info("User is already authenticated");
            findViewById(R.id.browser_sign_in_btn).setVisibility(View.GONE);
            findViewById(R.id.logout_btn).setVisibility(View.VISIBLE);
            showUserInfo();
            try {
                if (sessionClient.getTokens().isAccessTokenExpired()) {
                    // access_token expired hence refresh token
                    sessionClient.refreshToken(new RequestCallback<>() {
                        @Override
                        public void onSuccess(@NonNull Tokens result) {
                            logger.info("Token refreshed successfully");
                        }

                        @Override
                        public void onError(String msg, AuthorizationException error) {
                            logger.severe(String.format("Error: %s : %s", error.error, error.errorDescription));
                        }
                    });

                    /* sessionClient.introspectToken(sessionClient.getTokens().getRefreshToken(),
                            TokenTypeHint.ACCESS_TOKEN, new RequestCallback<>() {
                                @Override
                                public void onSuccess(@NonNull IntrospectInfo result) {
                                    var expirationTime = result.getExp();
                                    // check if expiration time has reached
                                }

                                @Override
                                public void onError(String error, AuthorizationException exception) {
                                    // handle request error
                                }
                            }
                    );*/
                }
            } catch (AuthorizationException error) {
                logger.severe(String.format("Error: %s : %s", error.error, error.errorDescription));
            }
        } else {
            sessionClient.clear();
        }
    }

    private void showUserInfo() {
        sessionClient.getUserProfile(new RequestCallback<>() {
            @Override
            public void onSuccess(@NonNull UserInfo result) {
                // handle UserInfo result.
                var user = (String) result.get("name");
                TextView textView = findViewById(R.id.have_account);
                textView.setText(String.format("%s %s", getString(R.string.welcome_user), user));
            }

            @Override
            public void onError(String msg, AuthorizationException error) {
                logger.severe(String.format("Error: %s : %s", error.error, error.errorDescription));
            }
        });
    }

    private void callServerApi() throws IOException, AuthorizationException {
        var token = sessionClient.getTokens();
        var accessToken = token.getAccessToken();

        var url = new URL("https://${resourceUrl}");
        var connection = (HttpURLConnection) url.openConnection();

        var bearerToken = String.format("Bearer %s", accessToken);
        connection.setRequestProperty("Authorization", bearerToken);
        connection.setRequestProperty("Accept", "application/json");

        try {
            var responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                logger.info("Authenticated successfully");
            } else {
                // handle error codes
            }
        } finally {
            connection.disconnect();
        }
    }
}
