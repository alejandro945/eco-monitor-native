package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.LOADING_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.MISMATCH_MESSAGE
import com.example.ecomonitor.domain.model.TransactionStatus.ErrorStatus
import com.example.ecomonitor.domain.model.TransactionStatus.LoadingStatus
import com.example.ecomonitor.data.repositories.AuthRepository
import com.example.ecomonitor.data.repositories.FirebaseAuthRepository
import com.example.ecomonitor.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val authRepository: AuthRepository = FirebaseAuthRepository()
): ViewModel() {
    private val _status = MutableLiveData<TransactionStatus>()
    val status: LiveData<TransactionStatus> get() = _status

    fun signUp(user: User) {
        if (user.password == user.repeatPassword) {
            _status.value = LoadingStatus(LOADING_MESSAGE)
            executeRepositorySignUp(user)
        } else {
            _status.value = ErrorStatus(MISMATCH_MESSAGE)
        }
    }

    private fun executeRepositorySignUp(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signUp(user)
            withContext(Dispatchers.Main) { _status.value = result }
        }
    }
}