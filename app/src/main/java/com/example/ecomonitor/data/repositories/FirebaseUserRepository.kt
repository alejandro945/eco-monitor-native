package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.AuthService
import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.PROFILE_DATA_SUCCESS
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.PROFILE_DATA_ERROR

class FirebaseUserRepository(
    private val authService: AuthService = FirebaseAuthService(),
    private val userStorage: IStorage<ProfileData> = FirebaseStorage("users")
): UserRepository {
    override suspend fun changeProfileData(profileData: ProfileData): AuthenticationStatus {
        authService.getUserUID()?.let { uid ->
            userStorage.update(uid, profileData)
            return AuthenticationStatus.SuccessStatus(PROFILE_DATA_SUCCESS)
        }
        return AuthenticationStatus.ErrorStatus(PROFILE_DATA_ERROR)
    }
}