package com.example.ecomonitor.data.services

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

/**
 * The service that handles authentication operations.
 * Base use cases:
 * - Sign in with email and password
 * - Sign in with credential for external providers
 * - Sign up with email and password
 * - Sign out
 */
interface AuthService {
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signIn(credential: AuthCredential): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun signOut(): Unit
}