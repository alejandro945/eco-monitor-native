package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.FirebaseUserRepository
import com.example.ecomonitor.data.repositories.UserRepository
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.AuthenticationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userRepository: UserRepository = FirebaseUserRepository()
): ViewModel() {
    private val _dataStatus = MutableLiveData<AuthenticationStatus>()
    private val _pictureStatus = MutableLiveData<AuthenticationStatus>()
    val dataStatus: LiveData<AuthenticationStatus> get() = _dataStatus
    val pictureStatus: LiveData<AuthenticationStatus> get() = _pictureStatus

    fun changeProfilePicture() {}

    fun changeProfileData(profileData: ProfileData) {
        _dataStatus.value = AuthenticationStatus.LoadingStatus(AuthenticationStatus.LOADING_MESSAGE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.changeProfileData(profileData)
            withContext(Dispatchers.Main) { _dataStatus.value = result }
        }
    }
}