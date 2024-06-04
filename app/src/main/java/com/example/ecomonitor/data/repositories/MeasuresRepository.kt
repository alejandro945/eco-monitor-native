package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.enum.MeasureUnit
import com.example.ecomonitor.domain.model.Measurement

interface MeasuresRepository {
    suspend fun getMeasurements(days: Int, measureUnit: MeasureUnit): List<Measurement>
}