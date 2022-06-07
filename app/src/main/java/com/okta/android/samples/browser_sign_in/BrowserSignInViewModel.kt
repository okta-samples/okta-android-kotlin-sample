package com.okta.android.samples.browser_sign_in

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okta.authfoundation.claims.name
import com.okta.authfoundation.client.OidcClientResult
import com.okta.authfoundationbootstrap.CredentialBootstrap
import com.okta.webauthenticationui.WebAuthenticationClient.Companion.createWebAuthenticationClient
import kotlinx.coroutines.launch
import timber.log.Timber

class BrowserSignInViewModel : ViewModel() {
    private val _state = MutableLiveData<BrowserState>(BrowserState.Loading)
    val state: LiveData<BrowserState> = _state

    init {
        viewModelScope.launch {
            _state.value = BrowserState.currentCredentialState()
        }
    }

    fun login(context: Context) {
        viewModelScope.launch {
            _state.value = BrowserState.Loading

            val result = CredentialBootstrap.oidcClient.createWebAuthenticationClient().login(
                context = context,
                redirectUrl = BuildConfig.SIGN_IN_REDIRECT_URI,
            )
            when (result) {
                is OidcClientResult.Error -> {
                    Timber.e(result.exception, "Failed to login.")
                    _state.value = BrowserState.currentCredentialState("Failed to login.")
                }
                is OidcClientResult.Success -> {
                    val credential = CredentialBootstrap.defaultCredential()
                    credential.storeToken(token = result.result)
                    _state.value = BrowserState.LoggedIn.create()
                }
            }
        }
    }

    fun logoutOfBrowser(context: Context) {
        viewModelScope.launch {
            _state.value = BrowserState.Loading

            val result = CredentialBootstrap.oidcClient.createWebAuthenticationClient().logoutOfBrowser(
                context = context,
                redirectUrl = BuildConfig.SIGN_OUT_REDIRECT_URI,
                CredentialBootstrap.defaultCredential().token?.idToken ?: "",
            )
            when (result) {
                is OidcClientResult.Error -> {
                    Timber.e(result.exception, "Failed to logout.")
                    _state.value = BrowserState.currentCredentialState("Failed to logout.")
                }
                is OidcClientResult.Success -> {
                    CredentialBootstrap.defaultCredential().delete()
                    _state.value = BrowserState.LoggedOut()
                }
            }
        }
    }
}

/**
 * Represents the current state of the [BrowserSignInViewModel].
 */
sealed class BrowserState {
    object Loading : BrowserState()
    class LoggedOut(val errorMessage: String? = null) : BrowserState()
    class LoggedIn private constructor(
        val name: String,
        val errorMessage: String?
    ) : BrowserState() {
        companion object {
            /**
             * Creates the [LoggedIn] state using the [CredentialBootstrap.defaultCredential]s ID Token name claim.
             */
            suspend fun create(errorMessage: String? = null): BrowserState {
                val credential = CredentialBootstrap.defaultCredential()
                val name = credential.idToken()?.name ?: ""
                return LoggedIn(name, errorMessage)
            }
        }
    }

    companion object {
        /**
         * Creates the [BrowserState] given the [CredentialBootstrap.defaultCredential]s presence of a token.
         *
         * @return Either [LoggedIn] or [LoggedOut].
         */
        suspend fun currentCredentialState(errorMessage: String? = null): BrowserState {
            val credential = CredentialBootstrap.defaultCredential()
            return if (credential.token == null) {
                LoggedOut(errorMessage)
            } else {
                LoggedIn.create(errorMessage)
            }
        }
    }
}
