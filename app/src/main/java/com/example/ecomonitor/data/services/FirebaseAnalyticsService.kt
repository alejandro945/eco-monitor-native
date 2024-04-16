package com.example.ecomonitor.data.services

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class FirebaseAnalyticsService(): AnalyticsService {
    override suspend fun logEvent(context: Context, eventName: String) {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        firebaseAnalytics.logEvent(eventName) { param(FirebaseAnalytics.Param.ITEM_ID, eventName) }
    }
}