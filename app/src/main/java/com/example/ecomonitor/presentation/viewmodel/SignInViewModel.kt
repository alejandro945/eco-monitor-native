package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.domain.model.AuthenticationStatus
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.EMPTY_TOKEN
import com.example.ecomonitor.domain.model.AuthenticationStatus.Companion.LOADING_MESSAGE
import com.example.ecomonitor.data.repositories.AuthRepository
import com.example.ecomonitor.data.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(
    private val authRepository: AuthRepository = FirebaseAuthRepository()
): ViewModel() {
    private val _status = MutableLiveData<AuthenticationStatus>()
    val status: LiveData<AuthenticationStatus> get() = _status

    fun signIn(email: String, password: String) {
        _status.value = AuthenticationStatus.LoadingStatus(LOADING_MESSAGE)
        executeRepositorySignIn(email, password)
    }

    private fun executeRepositorySignIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signIn(email, password)
            withContext(Dispatchers.Main) { _status.value = result }
        }
    }

    fun signIn(token: String?) {
        token?.let { t ->
            _status.value = AuthenticationStatus.LoadingStatus(LOADING_MESSAGE)
            executeRepositorySignIn(t)
        } ?: {
            _status.value = AuthenticationStatus.ErrorStatus(EMPTY_TOKEN)
        }
    }

    private fun executeRepositorySignIn(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signIn(token)
            withContext(Dispatchers.Main) { _status.value = result }
        }
    }
}