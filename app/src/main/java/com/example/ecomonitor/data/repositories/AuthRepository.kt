package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.domain.model.User

interface AuthRepository {
    suspend fun signOut() : AuthenticationStatus
    suspend fun signIn(email: String, password: String): AuthenticationStatus
    suspend fun signIn(token: String): AuthenticationStatus
    suspend fun signUp(user: User): AuthenticationStatus
}