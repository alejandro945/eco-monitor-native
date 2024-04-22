package com.example.ecomonitor.data.services

import android.content.Context

/**
 * Interface for logging events to an analytics service
 * Base use cases:
 * - Log an event
 * Post MVP use cases:
 * - Log an event with parameters
 * - Log an event with user properties
 * - Log an event with user properties and parameters
 */
interface AnalyticsService {
    suspend fun logEvent(context: Context, eventName: String)
}