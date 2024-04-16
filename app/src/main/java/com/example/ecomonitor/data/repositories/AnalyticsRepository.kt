package com.example.ecomonitor.data.repositories

import android.content.Context

interface AnalyticsRepository {
    suspend fun logEvent(context: Context, eventName: String)
}