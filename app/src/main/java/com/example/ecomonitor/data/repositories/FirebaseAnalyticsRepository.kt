package com.example.ecomonitor.data.repositories

import android.content.Context
import com.example.ecomonitor.data.services.AnalyticsService
import com.example.ecomonitor.data.services.FirebaseAnalyticsService

class FirebaseAnalyticsRepository(
    private val analyticsService: AnalyticsService = FirebaseAnalyticsService()
): AnalyticsRepository {
    override suspend fun logEvent(context: Context, eventName: String) {
        try { analyticsService.logEvent(context, eventName) }
        catch (_: Exception) { /* TODO. */ }
    }
}