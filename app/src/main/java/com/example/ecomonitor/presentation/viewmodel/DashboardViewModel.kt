package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.ecomonitor.data.repositories.FirebaseMeasuresRepository
import com.example.ecomonitor.data.repositories.MeasuresRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class DashboardViewModel(
    private val measuresRepository: MeasuresRepository = FirebaseMeasuresRepository()
): ViewModel() {
    private val _measurements = MutableLiveData<List<ValueDataEntry>>()
    val measurements: LiveData<List<ValueDataEntry>> get() = _measurements
    var measureUnit: String = ""

    fun getElectricalMeasurements(days: Int, pattern: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = measuresRepository.getElectricalMeasurements(days)
            val format = SimpleDateFormat(pattern, Locale.US)

            val listMap = result.groupBy { format.format(it.date) }
            val measurements = listMap.values.map { list ->
                var value = 0
                list.forEach { value += it.value }
                ValueDataEntry(format.format(list[0].date), value)
            }

            withContext(Dispatchers.Main){
                if (result.isNotEmpty()) { measureUnit = result[0].measureUnit }
                _measurements.value = measurements
            }
        }
    }
}