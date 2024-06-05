package com.example.ecomonitor.domain.model

import com.example.ecomonitor.domain.enum.ServiceType
import java.util.Date

data class Alert (
    val name: String = "",
    val type: ServiceType = ServiceType.WATER,
    val date: Date = Date(),
    val deviceUid: String = ""
)
