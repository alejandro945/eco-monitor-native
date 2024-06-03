package com.example.ecomonitor.domain.model

sealed class TransactionStatus {
    data class SuccessStatus(val message: String): TransactionStatus()
    data class ErrorStatus(val message: String): TransactionStatus()
    data class LoadingStatus(val message: String): TransactionStatus()

    companion object {
        const val ACCOUNT_CREATED_MESSAGE = "You have created your account! | "
        const val EMPTY_TOKEN = "The token couldn't be received."
        const val LOADING_MESSAGE = "Loading..."
        const val MISMATCH_MESSAGE = "The passwords you entered do not match."
        const val NULL_MESSAGE = "The user couldn't be retrieved, try again."
        const val SIGN_IN_SUCCESS_MESSAGE = "You have logged in successfully! | "
        const val EMPTY_FIELDS_MESSAGE = "One of the required fields is empty."
        const val SIGN_OUT_SUCCESS_MESSAGE = "You have logged out successfully! | "
        const val PROFILE_DATA_SUCCESS = "You have updated your profile successfully!"
        const val PROFILE_DATA_ERROR = "Your profile couldn't be updated due to an authentication error."
    }
}