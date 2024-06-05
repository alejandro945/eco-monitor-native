package com.example.ecomonitor.domain.model

import com.example.ecomonitor.domain.enum.ServiceType

data class Device (
    val name: String = "",
    val location: String = "",
    val type: ServiceType = ServiceType.ELECTRICITY,
)