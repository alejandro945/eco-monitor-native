package com.example.ecomonitor.util

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignInUtil {
    companion object {
        fun createGoogleSignInIntent(activity: Activity): Intent {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(GOOGLE_CLIENT_ID) // TODO - We need to remove this key from the codebase.
                .build()
            return GoogleSignIn.getClient(activity, gso).signInIntent
        }

        fun retrieveTokenFromIntent(intent: Intent): String? {
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent).result
            return account.idToken
        }

        private const val GOOGLE_CLIENT_ID = "478518882408-epgv6r1v3d1cnbfqiublqdes8b4tej3j.apps.googleusercontent.com"
    }
}