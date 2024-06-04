package com.example.ecomonitor.data.services

import com.google.firebase.firestore.QuerySnapshot

interface MeasuresService {
    suspend fun getMeasurements(key: String, days: Int, measureUnit: String): QuerySnapshot?
}