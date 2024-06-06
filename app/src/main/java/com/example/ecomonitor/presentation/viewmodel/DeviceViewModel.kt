package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.FirebaseDeviceRepository
import com.example.ecomonitor.data.repositories.IDeviceRepository
import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceViewModel(
    private val devicesRepository: IDeviceRepository = FirebaseDeviceRepository()
): ViewModel() {
    private val _devices = MutableLiveData<List<Device>>()
    val devices: LiveData<List<Device>> get() = _devices

    fun getDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = devicesRepository.getDevices()

            withContext(Dispatchers.Main) {
                _devices.value = result
            }
        }
    }

    fun addDevice(device: Device) {
        viewModelScope.launch(Dispatchers.IO) {
            devicesRepository.addDevice(device)
        }
    }


}