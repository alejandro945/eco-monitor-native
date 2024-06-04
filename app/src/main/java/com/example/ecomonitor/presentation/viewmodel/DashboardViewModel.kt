package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.ecomonitor.data.repositories.FirebaseMeasuresRepository
import com.example.ecomonitor.data.repositories.MeasuresRepository
import com.example.ecomonitor.domain.enum.MeasureUnit
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
    var mUnit: String = ""

    fun getElectricalMeasurements(days: Int, pattern: String, measureUnit: MeasureUnit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = measuresRepository.getElectricalMeasurements(days, measureUnit)
            val format = SimpleDateFormat(pattern, Locale.US)

            val listMap = result.groupBy { format.format(it.date) }
            val measurements = listMap.values.map { list ->
                var value = 0
                list.forEach { value += it.value }
                ValueDataEntry(format.format(list[0].date), value)
            }

            withContext(Dispatchers.Main){
                if (result.isNotEmpty()) { mUnit = measureUnit.toString() }
                _measurements.value = measurements
            }
        }
    }
}