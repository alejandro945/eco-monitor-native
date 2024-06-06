package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.services.IAuthService
import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.model.Device
import com.example.ecomonitor.domain.model.Measurement

class FirebaseDeviceRepository(
    private val authService: IAuthService = FirebaseAuthService(),
    private val deviceStorage: IStorage<Device> = FirebaseStorage("users/${authService.getUserUID()}/devices")
): IDeviceRepository {
    override suspend fun getDevices(): List<Device> {
        val devices = deviceStorage.list().documents

        val devicesMap = devices.map { it.toObject(Device::class.java) }

        return devicesMap.filterNotNull()
    }
}