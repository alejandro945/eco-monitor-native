package com.example.ecomonitor.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.AnalyticsRepository
import com.example.ecomonitor.data.repositories.FirebaseAnalyticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val analyticsRepository: AnalyticsRepository = FirebaseAnalyticsRepository()
): ViewModel() {
    fun logEvent(context: Context, eventName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            analyticsRepository.logEvent(context, eventName)
        }
    }
}