package com.example.ecomonitor.data.services

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class FirebaseAnalyticsService(): IAnalyticsService {
    /**
     * Logs an event to Firebase Analytics
     * @param context The context of the application
     * @param eventName The name of the event to log
     * @return The result of the log operation
     */
    override suspend fun logEvent(context: Context, eventName: String) {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        firebaseAnalytics.logEvent(eventName) { param(FirebaseAnalytics.Param.ITEM_ID, eventName) }
    }
}