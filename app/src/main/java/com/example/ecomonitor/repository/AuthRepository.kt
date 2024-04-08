package com.example.ecomonitor.repository

import com.example.ecomonitor.domain.model.AppAuthState
import com.example.ecomonitor.services.AuthServices
import com.google.firebase.auth.FirebaseAuthException


interface AuthRepository {
    suspend fun login(email: String, pass: String):AppAuthState
    suspend fun signup(email: String, pass: String): AppAuthState
    suspend fun loginWithGoogle(idToken: String): AppAuthState
}
class AuthRepositoryImpl(val service: AuthServices = AuthServices()) : AuthRepository {
    override suspend fun login(email:String, pass:String) : AppAuthState {
        try {
            val result = service.login(email, pass)
            result.user?.let {
                return AppAuthState.Success("")
            } ?: run {
                return AppAuthState.Error("")
            }
        }catch (ex: FirebaseAuthException){
            return AppAuthState.Error("")
        }
    }

    override suspend fun signup(email:String, pass:String) : AppAuthState {
        try {
            val result = service.signup(email, pass)
            result.user?.let {
                return AppAuthState.Success(it.uid)
            } ?: run {
                return AppAuthState.Error("Something went wrong")
            }
        }catch (ex: FirebaseAuthException){
            return AppAuthState.Error(ex.errorCode)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): AppAuthState {
        try {
            val result = service.loginWithGoogle(idToken)
            result.user?.let {
                return AppAuthState.Success(it.uid)
            } ?: run {
                return AppAuthState.Error("Something went wrong")
            }
        } catch (ex: FirebaseAuthException) {
            return AppAuthState.Error(ex.errorCode)
        }
    }
}