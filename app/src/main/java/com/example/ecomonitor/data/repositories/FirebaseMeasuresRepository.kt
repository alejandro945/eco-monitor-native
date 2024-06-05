package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.data.services.IAuthService
import com.example.ecomonitor.data.services.FirebaseAuthService
import com.example.ecomonitor.data.services.FirebaseMeasuresService
import com.example.ecomonitor.data.services.MeasuresService
import com.example.ecomonitor.domain.enum.Unit
import com.example.ecomonitor.domain.model.Measurement

class FirebaseMeasuresRepository(
    private val authService: IAuthService = FirebaseAuthService(),
    private val measuresService: MeasuresService = FirebaseMeasuresService()
): MeasuresRepository {
    override suspend fun getMeasurements(days: Int, unit: Unit): List<Measurement> {
        authService.getUserUID()?.let { uid ->
            val list = mutableListOf<Measurement>()

            val documents = measuresService.getMeasurements(uid, days, unit.toString())?.documents
            documents?.forEach { document ->
                val measurement = document.toObject(Measurement::class.java)
                measurement?.let { list.add(it) }
            }

            return list
        }
        return mutableListOf()
    }
}