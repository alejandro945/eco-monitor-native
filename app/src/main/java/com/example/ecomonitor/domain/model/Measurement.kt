package com.example.ecomonitor.domain.model

import com.example.ecomonitor.domain.enum.Unit
import java.util.Date

data class Measurement(
    val id: String = "",
    val date: Date = Date(),
    val value: Int = 0,
    val measureUnit: Unit = Unit.KWH,
    val deviceUid: String = ""
)
