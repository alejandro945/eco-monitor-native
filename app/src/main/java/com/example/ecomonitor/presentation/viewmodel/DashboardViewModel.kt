package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.ecomonitor.data.repositories.FirebaseMeasurementRepository
import com.example.ecomonitor.data.repositories.IMeasurementRepository
import com.example.ecomonitor.domain.enum.Unit
import com.example.ecomonitor.domain.model.Measurement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class DashboardViewModel(
    private val measuresRepository: IMeasurementRepository = FirebaseMeasurementRepository()
): ViewModel() {
    private val _measurements = MutableLiveData<List<ValueDataEntry>>()
    val measurements: LiveData<List<ValueDataEntry>> get() = _measurements

    private val _servicesMeasurements = MutableLiveData<MutableMap<String, List<ValueDataEntry>>>(
        mutableMapOf()
    )
    val servicesMeasurements: LiveData<MutableMap<String, List<ValueDataEntry>>> get() = _servicesMeasurements

    var mUnit: String = ""
        private set

    var queries = 0
        private set

    fun getMeasurements(
        days: Int,
        pattern: String,
        unit: Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = measuresRepository.getMeasurements(days, unit)
            val measurements = groupMeasuresByDateThenSum(result, pattern)

            withContext(Dispatchers.Main) {
                _measurements.value = measurements
                mUnit = unit.toString()
            }
        }
    }

    private fun groupMeasuresByDateThenSum(
        result: List<Measurement>,
        pattern: String
    ): List<ValueDataEntry> {
        val format = SimpleDateFormat(pattern, Locale.US)

        val listMap = result.groupBy { format.format(it.date) }

        val measurements = listMap.values.map { list ->
            var value = 0
            list.forEach { value += it.value }
            ValueDataEntry(format.format(list[0].date), value)
        }

        return measurements
    }

    fun getServicesMeasurements(
        days: Int,
        pattern: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val kwh = measuresRepository.getMeasurements(days, Unit.KWH)
            val m3 = measuresRepository.getMeasurements(days, Unit.M3)

            val kwhMeasurements = groupMeasuresByDateThenSum(kwh, pattern)
            val m3Measurements = groupMeasuresByDateThenSum(m3, pattern)

            withContext(Dispatchers.Main) {
                _servicesMeasurements.value?.set("kwh", kwhMeasurements)
                _servicesMeasurements.value?.set("m3", m3Measurements)
                _servicesMeasurements.value = _servicesMeasurements.value
            }
        }
    }

    fun addToQueriesCount() {
        queries++
    }
}