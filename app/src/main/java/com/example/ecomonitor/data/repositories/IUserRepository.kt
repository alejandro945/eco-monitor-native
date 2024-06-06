package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.Profile
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus

interface IUserRepository {
    suspend fun retrieveProfileData(): ProfileData?
    suspend fun changeProfileData(profileData: ProfileData): TransactionStatus

    fun getUserId() : String
    suspend fun getAllUsers(): List<Profile>
}