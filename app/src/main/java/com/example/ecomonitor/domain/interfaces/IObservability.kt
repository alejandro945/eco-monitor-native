package com.example.ecomonitor.domain.interfaces

interface IObservability {
    fun log(eventName: String): Unit
}