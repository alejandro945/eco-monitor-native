package com.example.ecomonitor.data.services

import com.google.firebase.firestore.QuerySnapshot

interface MeasuresService {
    suspend fun getElectricalMeasurements(key: String, days: Int): QuerySnapshot?
}