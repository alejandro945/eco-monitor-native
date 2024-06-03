package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.AuthenticationStatus

interface UserRepository {
    suspend fun changeProfileData(profileData: ProfileData): AuthenticationStatus
}