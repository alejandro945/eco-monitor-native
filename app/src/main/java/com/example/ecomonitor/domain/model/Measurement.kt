package com.example.ecomonitor.domain.model

import com.example.ecomonitor.domain.enum.Unit
import java.util.Date

data class Measurement(
    val date: Date,
    val value: Int,
    val measureUnit: Unit
)
