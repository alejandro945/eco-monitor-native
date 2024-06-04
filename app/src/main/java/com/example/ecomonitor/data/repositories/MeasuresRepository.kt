package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.Measurement

interface MeasuresRepository {
    suspend fun getElectricalMeasurements(days: Int): List<Measurement>
}