package com.example.ecomonitor.repositories

import com.example.ecomonitor.model.AuthenticationStatus
import com.example.ecomonitor.model.AuthenticationStatus.SuccessStatus
import com.example.ecomonitor.model.AuthenticationStatus.ErrorStatus
import com.example.ecomonitor.services.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuthRepository(
    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService()
): AuthRepository {
    override suspend fun signIn(token: String): AuthenticationStatus {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            val user = firebaseAuthService.signIn(credential).user
            SuccessStatus(SUCCESS_MESSAGE + user!!.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
    }

    override suspend fun signUp(email: String, password: String): AuthenticationStatus {
        return try {
            val user = firebaseAuthService.signUp(email, password).user
            SuccessStatus(SUCCESS_MESSAGE + user!!.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
    }

    companion object {
        private const val SUCCESS_MESSAGE = "You have created your account! | "
        private const val NULL_MESSAGE = "The user couldn't be retrieved, try again."
    }
}