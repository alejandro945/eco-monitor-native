package com.example.ecomonitor.services

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthServices {

    suspend fun signup(email:String, pass:String) : AuthResult {
        return Firebase.auth.createUserWithEmailAndPassword(email, pass).await()
    }

    suspend fun login(email:String, pass:String) : AuthResult{
        return Firebase.auth.signInWithEmailAndPassword(email, pass).await()
    }

    // TODO: Implement loginWithGoogle -> This is an Autogenerated function
    suspend fun loginWithGoogle(idToken:String) : AuthResult{
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return Firebase.auth.signInWithCredential(credential).await()
    }

    // TODO: Implement Login with apple account

}