package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.LOADING_MESSAGE
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.MISMATCH_MESSAGE
import com.example.ecomonitor.domain.model.AuthenticationStatus.ErrorStatus
import com.example.ecomonitor.domain.model.AuthenticationStatus.LoadingStatus
import com.example.ecomonitor.data.repositories.AuthRepository
import com.example.ecomonitor.data.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val authRepository: AuthRepository = FirebaseAuthRepository()
): ViewModel() {
    private val _status = MutableLiveData<AuthenticationStatus>()
    val status: LiveData<AuthenticationStatus> get() = _status

    fun signUp(email: String, password: String, repeatPassword: String) {
        if (password == repeatPassword) {
            _status.value = LoadingStatus(LOADING_MESSAGE)
            executeRepositorySignUp(email, password)
        } else {
            _status.value = ErrorStatus(MISMATCH_MESSAGE)
        }
    }

    private fun executeRepositorySignUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signUp(email, password)
            withContext(Dispatchers.Main) { _status.value = result }
        }
    }
}