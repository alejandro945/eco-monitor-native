package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.ACCOUNT_CREATED_MESSAGE
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.EMPTY_FIELDS_MESSAGE
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.NULL_MESSAGE
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.SIGN_IN_SUCCESS_MESSAGE
import com.example.ecomonitor.domain.model.AuthenticationStatus.SuccessStatus
import com.example.ecomonitor.domain.model.AuthenticationStatus.ErrorStatus
import com.example.ecomonitor.data.services.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuthRepository(
    private val firebaseAuthService: FirebaseAuthService = FirebaseAuthService()
): AuthRepository {
    override suspend fun signIn(email: String, password: String): AuthenticationStatus {
        return try {
            val user = firebaseAuthService.signIn(email, password).user
            SuccessStatus(SIGN_IN_SUCCESS_MESSAGE + user!!.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
        catch (exception: IllegalArgumentException) { ErrorStatus(EMPTY_FIELDS_MESSAGE) }
    }

    override suspend fun signIn(token: String): AuthenticationStatus {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            val user = firebaseAuthService.signIn(credential).user
            SuccessStatus(SIGN_IN_SUCCESS_MESSAGE + user!!.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
        catch (exception: IllegalArgumentException) { ErrorStatus(EMPTY_FIELDS_MESSAGE) }
    }

    override suspend fun signUp(email: String, password: String): AuthenticationStatus {
        return try {
            val user = firebaseAuthService.signUp(email, password).user
            SuccessStatus(ACCOUNT_CREATED_MESSAGE + user!!.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
        catch (exception: IllegalArgumentException) { ErrorStatus(EMPTY_FIELDS_MESSAGE) }
    }
}