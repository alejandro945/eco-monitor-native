package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.FirebaseUserRepository
import com.example.ecomonitor.data.repositories.IUserRepository
import com.example.ecomonitor.domain.model.Profile
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SystemUsersViewModel(
    private val userRepository: IUserRepository = FirebaseUserRepository()
): ViewModel() {

    private val _users = MutableLiveData<List<Profile>>()
    val users: LiveData<List<Profile>> get() = _users

    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val usersList = userRepository.getAllUsers()
            _users.postValue(usersList)
        }
    }
}