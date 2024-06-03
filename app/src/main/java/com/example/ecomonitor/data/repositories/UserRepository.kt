package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus

interface UserRepository {
    suspend fun retrieveProfileData(): ProfileData?
    suspend fun changeProfileData(profileData: ProfileData): TransactionStatus
}