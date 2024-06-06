package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.domain.model.Measurement

interface IDeviceRepository {
    suspend fun getDevices(): List<Device>

    suspend fun addDevice(device: Device)
}