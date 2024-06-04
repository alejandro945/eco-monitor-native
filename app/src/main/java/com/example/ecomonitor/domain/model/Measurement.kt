package com.example.ecomonitor.domain.model

import java.util.Date

data class Measurement(
    val id: String = "",
    val timestamp: Date = Date(),
    val value: Int = 0
)
