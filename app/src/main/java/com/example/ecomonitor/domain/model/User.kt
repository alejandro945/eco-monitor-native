package com.example.ecomonitor.domain.model

import com.example.ecomonitor.domain.enum.Role

data class User(
    val name: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
    val role: Role
)
