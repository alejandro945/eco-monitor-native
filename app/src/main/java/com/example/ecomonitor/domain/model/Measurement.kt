package com.example.ecomonitor.domain.model

import java.util.Date

data class Measurement(
    val id: String = "",
    val date: Date = Date(),
    val value: Int = 0,
    val measureUnit: String = ""
)
