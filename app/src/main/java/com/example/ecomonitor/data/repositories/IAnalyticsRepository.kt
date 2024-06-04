package com.example.ecomonitor.data.repositories

import android.content.Context

interface IAnalyticsRepository {
    suspend fun logEvent(context: Context, eventName: String)
}