package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.services.IAuthService
import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.model.Device

class FirebaseDeviceRepository(
    private val authService: IAuthService = FirebaseAuthService(),
    private val deviceStorage: IStorage<Device> = FirebaseStorage("users/${authService.getUserUID()}/devices")
): IDeviceRepository {
    override suspend fun getDevices(): List<Device> {
        return try {
            val devices = deviceStorage.list().documents
            devices.mapNotNull { it.toObject(Device::class.java) }
        } catch (e: Exception) {
            emptyList()
        }

    }
}