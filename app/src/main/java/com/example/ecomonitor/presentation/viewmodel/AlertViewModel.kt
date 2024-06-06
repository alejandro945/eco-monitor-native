package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.FirebaseAlertRepository
import com.example.ecomonitor.data.repositories.FirebaseDeviceRepository
import com.example.ecomonitor.data.repositories.IAlertRepository
import com.example.ecomonitor.data.repositories.IDeviceRepository
import com.example.ecomonitor.domain.model.Alert
import com.example.ecomonitor.domain.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertViewModel(
    private val alertsRepository: IAlertRepository = FirebaseAlertRepository()
): ViewModel() {
    private val _alerts = MutableLiveData<List<Alert>>()
    val alerts: LiveData<List<Alert>> get() = _alerts

    fun getAlerts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = alertsRepository.getAlerts()
            _alerts.postValue(result)
        }
    }


}