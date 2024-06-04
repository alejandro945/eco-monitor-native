package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.Measurement

interface IMeasurementService {
    fun observe(listener: (Measurement) -> Unit)
}