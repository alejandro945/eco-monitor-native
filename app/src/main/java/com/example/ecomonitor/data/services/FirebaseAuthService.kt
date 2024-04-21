package com.example.ecomonitor.data.services

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(
    private val service: FirebaseAuth = Firebase.auth
): AuthService {
    override suspend fun signIn(email: String, password: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(credential: AuthCredential): AuthResult {
        return service.signInWithCredential(credential).await()
    }

    override suspend fun signUp(email: String, password: String): AuthResult {
        return service.createUserWithEmailAndPassword(email, password).await()
    }
}