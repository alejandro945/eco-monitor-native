package com.example.ecomonitor.repositories

import com.example.ecomonitor.model.AuthenticationStatus

interface AuthRepository {
    suspend fun signIn(token: String): AuthenticationStatus
    suspend fun signUp(email: String, password: String): AuthenticationStatus
}