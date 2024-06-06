package com.example.ecomonitor.data.repositories


import com.example.ecomonitor.domain.model.Alert

interface IAlertRepository {
    suspend fun getAlerts(): List<Alert>
}