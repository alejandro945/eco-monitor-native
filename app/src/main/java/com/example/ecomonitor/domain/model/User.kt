package com.example.ecomonitor.domain.model

import com.example.ecomonitor.domain.enum.Role

data class User(
    val name: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
    val role: Role
)

data class Profile(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: Role = Role.CLIENTE
)

data class ProfileData(
    val documentId: Long = -1,
    val name: String = "",
    val address: String = "",
    val email: String = "",
    val phone: Long = -1,
    val age: Long = -1
)