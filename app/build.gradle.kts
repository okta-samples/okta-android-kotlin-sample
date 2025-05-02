import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 35

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    defaultConfig {
        applicationId = "com.okta.android.samples.browser_sign_in"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    namespace = "com.okta.android.samples.browser_sign_in"
}

dependencies {
    // Android studio doesn't support newer versions?
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.kotlin.stdlib.v1925)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.timber)

    implementation(platform("com.okta.kotlin:bom:1.0.0"))
    implementation(libs.okta.auth.foundation.bootstrap)
    implementation(libs.okta.web.authentication.ui)
}

val oktaProperties = Properties().apply {
    load(FileInputStream("okta.properties"))
}

fun parseScheme(uri: String): String {
    val index = uri.indexOf(":/")
    if (index == -1) {
        throw IllegalStateException("Scheme is not in a valid format.")
    }
    return uri.substring(0, index)
}

android.defaultConfig {
    buildConfigField("String", "DISCOVERY_URL", "\"${oktaProperties.getProperty("discoveryUrl")}\"")
    buildConfigField("String", "CLIENT_ID", "\"${oktaProperties.getProperty("clientId")}\"")
    buildConfigField("String", "SIGN_IN_REDIRECT_URI", "\"${oktaProperties.getProperty("signInRedirectUri")}\"")
    buildConfigField("String", "SIGN_OUT_REDIRECT_URI", "\"${oktaProperties.getProperty("signOutRedirectUri")}\"")
    manifestPlaceholders["webAuthenticationRedirectScheme"] =  parseScheme(oktaProperties.getProperty("signInRedirectUri"))
}