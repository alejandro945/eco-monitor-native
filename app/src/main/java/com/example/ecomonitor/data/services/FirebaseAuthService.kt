package com.example.ecomonitor.data.services

import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(
    private val service: FirebaseAuth = Firebase.auth
): AuthService {
    /**
     * Changes the current user's password to a new one.
     * @param password The new password of the user.
     */
    override suspend fun updatePassword(password: String) {
        service.currentUser?.updatePassword(password)?.await()
    }

    /**
     * Returns the signed in user's UID or null if it doesn't exist.
     * @return The signed in user's UID or null if it doesn't exist.
     */
    override fun getUserUID(): String? { return service.uid }

    /**
     * Signs out the user from the app.
    */
    override fun signOut() { service.signOut() }

    /**
     * Signs in the user with the given email and password using a local account.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The result of the sign in operation.
     */
    override suspend fun signIn(email: String, password: String): AuthResult {
        return service.signInWithEmailAndPassword(email, password).await()
    }

    /**
     * Signs in the user with the given credential.
     * This is used for SSO sign in.
     * @param credential The credential to sign in with.
     * @return The result of the sign in operation.
     */
    override suspend fun signIn(credential: AuthCredential): AuthResult {
        return service.signInWithCredential(credential).await()
    }

    /**
     * Signs up the user with the given email and password using a local account.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The result of the sign up operation.
     */
    override suspend fun signUp(email: String, password: String): AuthResult {
       return service.createUserWithEmailAndPassword(email, password).await()
    }
}