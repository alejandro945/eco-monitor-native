package com.example.ecomonitor.data.services

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class FirebaseMeasuresService: MeasuresService {
    //TODO. DELETE.
    override suspend fun getMeasurements(key: String, days: Int, measureUnit: String): QuerySnapshot? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)

        val collection = Firebase.firestore
            .collection("users")
            .document(key)
            .collection("measurements")

        return collection
            .whereEqualTo("measureUnit", measureUnit)
            .whereGreaterThanOrEqualTo("date", calendar.time)
            .orderBy("date")
            .get().await()
    }
}