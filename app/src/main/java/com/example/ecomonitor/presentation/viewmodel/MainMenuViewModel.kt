package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.AuthRepository
import com.example.ecomonitor.data.repositories.FirebaseAuthRepository
import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.LOADING_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenuViewModel(
    private val authRepository: AuthRepository = FirebaseAuthRepository(),
) : ViewModel(){

    private val _status = MutableLiveData<AuthenticationStatus>()
    val status: LiveData<AuthenticationStatus> get() = _status

    fun signOut() {
        _status.value = AuthenticationStatus.LoadingStatus(LOADING_MESSAGE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signOut()
            withContext(Dispatchers.Main){ _status.value = result }
        }
    }
}