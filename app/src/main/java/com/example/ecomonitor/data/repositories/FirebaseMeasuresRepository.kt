package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.AuthService
import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.services.FirebaseMeasuresService
import com.example.ecomonitor.data.services.MeasuresService
import com.example.ecomonitor.domain.model.Measurement

class FirebaseMeasuresRepository(
    private val authService: AuthService = FirebaseAuthService(),
    private val measuresService: MeasuresService = FirebaseMeasuresService()
): MeasuresRepository {
    override suspend fun getElectricalMeasurements(days: Int): List<Measurement> {
        authService.getUserUID()?.let { uid ->
            val list = mutableListOf<Measurement>()

            val documents = measuresService.getElectricalMeasurements(uid, days)?.documents
            documents?.forEach { document ->
                val measurement = document.toObject(Measurement::class.java)
                measurement?.let { list.add(it) }
            }

            return list
        }
        return mutableListOf()
    }
}