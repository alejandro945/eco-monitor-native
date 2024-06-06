package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.IAuthService
import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.model.Profile
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.PROFILE_DATA_SUCCESS
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.PROFILE_DATA_ERROR
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository(
    private val authService: IAuthService = FirebaseAuthService(),
    private val userStorage: IStorage<ProfileData> = FirebaseStorage("users")
): IUserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun retrieveProfileData(): ProfileData? {
        authService.getUserUID()?.let { uid ->
            val profileData = userStorage.get(uid)
            return profileData.toObject(ProfileData::class.java)
        }
        return null
    }

    override suspend fun changeProfileData(profileData: ProfileData): TransactionStatus {
        authService.getUserUID()?.let { uid ->
            userStorage.update(uid, profileData)
            return TransactionStatus.SuccessStatus(PROFILE_DATA_SUCCESS)
        }
        return TransactionStatus.ErrorStatus(PROFILE_DATA_ERROR)
    }

    override suspend fun getAllUsers(): List<Profile> {
        return try {
            val result = userStorage.list()
            result.documents.mapNotNull { it.toObject(Profile::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}