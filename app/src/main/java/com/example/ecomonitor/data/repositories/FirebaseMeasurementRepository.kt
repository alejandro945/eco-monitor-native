package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.storage.FirebaseStorage
import com.example.ecomonitor.data.storage.IStorage
import com.example.ecomonitor.domain.model.Measurement

class FirebaseMeasurementRepository(
    private val userId: String,
    private val measurementStorage: IStorage<Measurement> = FirebaseStorage("users/$userId/measurements")
): IMeasurementRepository {
    override fun observe(listener: (Measurement) -> Unit) {
        measurementStorage.observe{
            val message = it.toObject(Measurement::class.java)
            listener(message)
        }
    }
}