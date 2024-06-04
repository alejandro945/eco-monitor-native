package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.FirebaseUserRepository
import com.example.ecomonitor.data.repositories.IUserRepository
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.LOADING_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userRepository: IUserRepository = FirebaseUserRepository()
): ViewModel() {
    private val _profileData = MutableLiveData<ProfileData?>()
    private val _dataStatus = MutableLiveData<TransactionStatus>()
    private val _pictureStatus = MutableLiveData<TransactionStatus>()

    val profileData: MutableLiveData<ProfileData?> get() = _profileData
    val dataStatus: LiveData<TransactionStatus> get() = _dataStatus
    val pictureStatus: LiveData<TransactionStatus> get() = _pictureStatus

    fun changeProfilePicture() {}

    fun retrieveProfileData() {
        viewModelScope.launch(Dispatchers.IO) {
            val profileData = userRepository.retrieveProfileData()
            withContext(Dispatchers.Main) { _profileData.value = profileData }
        }
    }

    fun changeProfileData(profileData: ProfileData) {
        _dataStatus.value = LoadingStatus(LOADING_MESSAGE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.changeProfileData(profileData)
            withContext(Dispatchers.Main) { _dataStatus.value = result }
        }
    }
}