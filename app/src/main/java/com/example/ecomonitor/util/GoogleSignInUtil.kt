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
                .requestIdToken(GOOGLE_SIGN_IN_KEY) // TODO - We need to remove this key from the codebase.
                .build()
            return GoogleSignIn.getClient(activity, gso).signInIntent
        }

        fun retrieveTokenFromIntent(intent: Intent): String? {
            val account = GoogleSignIn.getSignedInAccountFromIntent(intent).result
            return account.idToken
        }

        private const val GOOGLE_SIGN_IN_KEY = "390179189698-rjd3v42it2rv1sd8dvruap6jh5pspnih.apps.googleusercontent.com"
    }
}