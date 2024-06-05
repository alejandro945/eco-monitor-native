package com.example.ecomonitor.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.IAnalyticsRepository
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.EMPTY_TOKEN
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.LOADING_MESSAGE
import com.example.ecomonitor.data.repositories.IAuthRepository
import com.example.ecomonitor.data.repositories.FirebaseAnalyticsRepository
import com.example.ecomonitor.data.repositories.FirebaseAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(
    private val authRepository: IAuthRepository = FirebaseAuthRepository(),
    private val analyticsRepository: IAnalyticsRepository = FirebaseAnalyticsRepository()
): ViewModel() {
    private val _status = MutableLiveData<TransactionStatus>()
    val status: LiveData<TransactionStatus> get() = _status

    fun logEvent(context: Context, eventName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            analyticsRepository.logEvent(context, eventName)
        }
    }

    fun signIn(email: String, password: String) {
        executeRepositorySignIn(email, password)
    }

    private fun executeRepositorySignIn(email: String, password: String) {
        _status.value = TransactionStatus.LoadingStatus(LOADING_MESSAGE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signIn(email, password)
            withContext(Dispatchers.Main) { _status.value = result }
        }
    }

    fun signIn(token: String?) {
        token?.let { t -> executeRepositorySignIn(t) }
        ?: { _status.value = TransactionStatus.ErrorStatus(EMPTY_TOKEN) }
    }

    private fun executeRepositorySignIn(token: String) {
        _status.value = TransactionStatus.LoadingStatus(LOADING_MESSAGE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signIn(token)
            withContext(Dispatchers.Main) { _status.value = result }
        }
    }
}