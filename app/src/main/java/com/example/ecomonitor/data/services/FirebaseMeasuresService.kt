package com.example.ecomonitor.data.services

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class FirebaseMeasuresService: MeasuresService {
    override suspend fun getElectricalMeasurements(key: String, days: Int): QuerySnapshot? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)

        val collection = Firebase.firestore
            .collection("users")
            .document(key)
            .collection("electricalMeasurements")

        return collection
            .whereGreaterThanOrEqualTo("timestamp", calendar.time)
            .orderBy("timestamp")
            .get().await()
    }
}