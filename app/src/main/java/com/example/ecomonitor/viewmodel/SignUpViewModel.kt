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
        token?.let {
            _status.value = LoadingStatus(LOADING_MESSAGE)
            viewModelScope.launch(Dispatchers.IO) {
                val value = authRepository.signIn(it)
                withContext(Dispatchers.Main) { _status.value = value }
            }
        } ?: { ErrorStatus(EMPTY_TOKEN) }
    }

    fun signUp(email: String, password: String, repeatPassword: String) {
        if (password != repeatPassword) {
            _status.value = ErrorStatus(MISMATCH_MESSAGE)
            return
        } else {
            _status.value = LoadingStatus(LOADING_MESSAGE)
            viewModelScope.launch(Dispatchers.IO) {
                val value = authRepository.signUp(email, password)
                withContext(Dispatchers.Main) { _status.value = value }
            }
        }
    }

    companion object {
        private const val EMPTY_TOKEN = "The token couldn't be received."
        private const val LOADING_MESSAGE = "Loading..."
        private const val MISMATCH_MESSAGE = "The passwords you entered do not match."
    }
}