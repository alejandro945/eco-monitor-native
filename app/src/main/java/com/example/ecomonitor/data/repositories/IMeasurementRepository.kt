package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.Measurement

interface IMeasurementRepository {
    fun observe(listener: (Measurement) -> Unit)
}