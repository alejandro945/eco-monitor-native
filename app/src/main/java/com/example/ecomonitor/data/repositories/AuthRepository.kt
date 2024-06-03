package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.User

interface AuthRepository {
    suspend fun updatePassword(password: String): TransactionStatus
    suspend fun signOut() : TransactionStatus
    suspend fun signIn(email: String, password: String): TransactionStatus
    suspend fun signIn(token: String): TransactionStatus
    suspend fun signUp(user: User): TransactionStatus
}