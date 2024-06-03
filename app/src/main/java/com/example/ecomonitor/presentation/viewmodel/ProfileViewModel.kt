package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.FirebaseUserRepository
import com.example.ecomonitor.data.repositories.UserRepository
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userRepository: UserRepository = FirebaseUserRepository()
): ViewModel() {
    private val _dataStatus = MutableLiveData<TransactionStatus>()
    private val _pictureStatus = MutableLiveData<TransactionStatus>()
    val dataStatus: LiveData<TransactionStatus> get() = _dataStatus
    val pictureStatus: LiveData<TransactionStatus> get() = _pictureStatus

    fun changeProfilePicture() {}

    fun changeProfileData(profileData: ProfileData) {
        _dataStatus.value = TransactionStatus.LoadingStatus(TransactionStatus.LOADING_MESSAGE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.changeProfileData(profileData)
            withContext(Dispatchers.Main) { _dataStatus.value = result }
        }
    }
}