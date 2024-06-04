package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.IAuthService
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.ACCOUNT_CREATED_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.EMPTY_FIELDS_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.NULL_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.SIGN_IN_SUCCESS_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.SIGN_OUT_SUCCESS_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.SuccessStatus
import com.example.ecomonitor.domain.model.TransactionStatus.ErrorStatus
import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.enum.Role
import com.example.ecomonitor.domain.model.Profile
import com.example.ecomonitor.domain.model.User
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuthRepository(
    private val authService: IAuthService = FirebaseAuthService(),
    private val userStorage: IStorage<Profile> = FirebaseStorage("users")
): IAuthRepository {
    override suspend fun updatePassword(password: String): TransactionStatus {
        return try {
            authService.updatePassword(password)
            return SuccessStatus("You have changed your password successfully!")
        }
        catch (e:IllegalArgumentException) { ErrorStatus(e.message!!) }
        catch (e: FirebaseAuthWeakPasswordException) { ErrorStatus(e.message!!) }
        catch (_:Exception) { ErrorStatus("An error has occurred (have you tried to sign in again?).") }
    }

    override suspend fun signOut(): TransactionStatus {
        return try {
            authService.signOut()
            SuccessStatus(SIGN_OUT_SUCCESS_MESSAGE)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
    }

    override suspend fun signIn(email: String, password: String): TransactionStatus {
        return try {
            val user = authService.signIn(email, password).user
            SuccessStatus(SIGN_IN_SUCCESS_MESSAGE + user!!.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
        catch (exception: IllegalArgumentException) { ErrorStatus(EMPTY_FIELDS_MESSAGE) }
    }

    override suspend fun signIn(token: String): TransactionStatus {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            val user = authService.signIn(credential).user

            //Validates that a profile exists for the user and, if it doesn't, creates it.
            val profile = userStorage.get(user!!.uid).toObject(Profile::class.java)
            if (profile == null) {
                userStorage.save(user.uid, Profile(user.uid, user.displayName ?: "", user.email ?: "", Role.CLIENTE))
            }

            SuccessStatus(SIGN_IN_SUCCESS_MESSAGE + user.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
        catch (exception: IllegalArgumentException) { ErrorStatus(EMPTY_FIELDS_MESSAGE) }
    }

    override suspend fun signUp(user: User): TransactionStatus {
        return try {
            val authUser = authService.signUp(user.email, user.password).user

            authUser?.let {
                userStorage.save(it.uid, Profile(it.uid, user.name, user.email, user.role))
            }

            SuccessStatus(ACCOUNT_CREATED_MESSAGE + user.email)
        }
        catch (exception: FirebaseAuthException) { ErrorStatus(exception.errorCode) }
        catch (exception: NullPointerException) { ErrorStatus(NULL_MESSAGE) }
        catch (exception: IllegalArgumentException) { ErrorStatus(EMPTY_FIELDS_MESSAGE) }
    }
}