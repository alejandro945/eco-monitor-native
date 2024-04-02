package com.example.ecomonitor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.model.AuthenticationStatus
import com.example.ecomonitor.model.AuthenticationStatus.ErrorStatus
import com.example.ecomonitor.model.AuthenticationStatus.LoadingStatus
import com.example.ecomonitor.repositories.AuthRepository
import com.example.ecomonitor.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val authRepository: AuthRepository = FirebaseAuthRepository()
): ViewModel() {
    private val _status = MutableLiveData<AuthenticationStatus>()
    val status: LiveData<AuthenticationStatus> get() = _status

    fun signIn(token: String?) {
        token?.let { t ->
            _status.value = LoadingStatus(LOADING_MESSAGE)
            executeRepositorySignIn(t)
        } ?: {
            _status.value = ErrorStatus(EMPTY_TOKEN)
        }
    }

    private fun executeRepositorySignIn(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signIn(token)
            withContext(Dispatchers.Main) { _status.value = result }
        }
    }

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

    companion object {
        private const val EMPTY_TOKEN = "The token couldn't be received."
        private const val LOADING_MESSAGE = "Loading..."
        private const val MISMATCH_MESSAGE = "The passwords you entered do not match."
    }
}