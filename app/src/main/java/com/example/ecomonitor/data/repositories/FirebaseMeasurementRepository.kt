package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.services.IAuthService
import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.model.Measurement
import com.google.firebase.firestore.Filter
import java.util.Calendar

class FirebaseMeasurementRepository(
    private val authService: IAuthService = FirebaseAuthService(),
    private val measurementStorage: IStorage<Measurement> = FirebaseStorage("users/${authService.getUserUID()}/measurements")
): IMeasurementRepository {
    override suspend fun getMeasurements(days: Int, unit: com.example.ecomonitor.domain.enum.Unit): List<Measurement> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)

        val documents = measurementStorage.query(listOf(
            Filter.greaterThanOrEqualTo("date", calendar.time),
            Filter.equalTo("measureUnit", unit.toString())
        )).documents

        val list = mutableListOf<Measurement>()

        documents.forEach { document ->
            val measurement = document.toObject(Measurement::class.java)
            measurement?.let { list.add(it) }
        }

        return list
    }

    override fun observe(listener: (Measurement) -> Unit) {
        measurementStorage.observe{
            val message = it.toObject(Measurement::class.java)
            listener(message)
        }
    }
}