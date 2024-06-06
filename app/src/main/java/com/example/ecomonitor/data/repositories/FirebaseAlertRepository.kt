package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.services.IAuthService
import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.model.Alert
import com.example.ecomonitor.domain.model.Device

class FirebaseAlertRepository(
    private val authService: IAuthService = FirebaseAuthService(),
    private val alertStorage: IStorage<Alert> = FirebaseStorage("users/${authService.getUserUID()}/alerts")
): IAlertRepository {
    override suspend fun getAlerts(): List<Alert> {
        return try {
            val alerts = alertStorage.list().documents
            alerts.mapNotNull { it.toObject(Alert::class.java) }
        } catch (e: Exception) {
            emptyList()
        }

    }
}