package com.example.ecomonitor.data.services

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

interface AuthService {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signIn(credential: AuthCredential): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
}