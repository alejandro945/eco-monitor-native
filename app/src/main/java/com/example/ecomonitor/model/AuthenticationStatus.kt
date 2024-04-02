package com.example.ecomonitor.model

sealed class AuthenticationStatus {
    data class SuccessStatus(val message: String): AuthenticationStatus()
    data class ErrorStatus(val message: String): AuthenticationStatus()
    data class LoadingStatus(val message: String): AuthenticationStatus()
}