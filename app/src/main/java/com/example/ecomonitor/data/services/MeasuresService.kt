package com.example.ecomonitor.data.services

import com.google.firebase.firestore.QuerySnapshot

interface MeasuresService {
    //TODO. DELETE.
    suspend fun getMeasurements(key: String, days: Int, measureUnit: String): QuerySnapshot?
}