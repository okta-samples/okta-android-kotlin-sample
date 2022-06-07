package com.okta.android.samples.browser_sign_in

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class BrowserSignInActivity : AppCompatActivity() {
    private val viewModel by viewModels<BrowserSignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser_sign_in)

        val progressBar = findViewById<View>(R.id.progress_bar)
        val statusTextView = findViewById<TextView>(R.id.status_text_view)
        val loginButton = findViewById<View>(R.id.login_button)
        val logoutOfBrowserButton = findViewById<View>(R.id.logout_of_browser_button)

        loginButton.setOnClickListener {
            viewModel.login(this)
        }
        logoutOfBrowserButton.setOnClickListener {
            viewModel.logoutOfBrowser(this)
        }

        /**
         * Observe the [BrowserSignInViewModel]s [BrowserState] for changes.
         *
         * Changes are initialized by the button clicks above, and happen asynchronously once the user completes the flows.
         *
         * Once the change occurs, show the user the correct UI.
         */
        viewModel.state.observe(this) { state ->
            when (state) {
                is BrowserState.LoggedIn -> {
                    progressBar.visibility = View.GONE
                    loginButton.visibility = View.GONE
                    logoutOfBrowserButton.visibility= View.VISIBLE
                    statusTextView.visibility = View.VISIBLE
                    if (state.errorMessage == null) {
                        statusTextView.text = getString(R.string.welcome_user, state.name)
                    } else {
                        statusTextView.text = state.errorMessage
                    }
                }
                is BrowserState.LoggedOut -> {
                    progressBar.visibility = View.GONE
                    loginButton.visibility = View.VISIBLE
                    logoutOfBrowserButton.visibility= View.GONE
                    statusTextView.visibility = View.VISIBLE
                    if (state.errorMessage == null) {
                        statusTextView.text = getString(R.string.have_account)
                    } else {
                        statusTextView.text = state.errorMessage
                    }
                }
                BrowserState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    loginButton.visibility = View.GONE
                    logoutOfBrowserButton.visibility= View.GONE
                    statusTextView.visibility = View.GONE
                }
            }
        }
    }
}
