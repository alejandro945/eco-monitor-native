package com.example.ecomonitor.data.services

import android.content.Context

interface AnalyticsService {
    suspend fun logEvent(context: Context, eventName: String)
}