package com.example.ecomonitor.data.repositories

import android.content.Context
import com.example.ecomonitor.data.services.IAnalyticsService
import com.example.ecomonitor.data.services.FirebaseAnalyticsService

class FirebaseAnalyticsRepository(
    private val analyticsService: IAnalyticsService = FirebaseAnalyticsService()
): IAnalyticsRepository {
    override suspend fun logEvent(context: Context, eventName: String) {
        try { analyticsService.logEvent(context, eventName) }
        catch (_: Exception) { /* TODO. */ }
    }
}