package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.AuthenticationStatus

interface AuthRepository {
    suspend fun signIn(email: String, password: String): AuthenticationStatus
    suspend fun signIn(token: String): AuthenticationStatus
    suspend fun signUp(email: String, password: String): AuthenticationStatus
}