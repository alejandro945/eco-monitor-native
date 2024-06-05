package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.enum.Unit
import com.example.ecomonitor.domain.model.Measurement

interface MeasuresRepository {
    suspend fun getMeasurements(days: Int, unit: Unit): List<Measurement>
}