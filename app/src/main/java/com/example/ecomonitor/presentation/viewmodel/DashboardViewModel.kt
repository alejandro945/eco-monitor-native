package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.ecomonitor.data.repositories.FirebaseMeasuresRepository
import com.example.ecomonitor.data.repositories.MeasuresRepository
import com.example.ecomonitor.domain.enum.MeasureUnit
import com.example.ecomonitor.domain.model.Measurement
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
        private set

    var queries = 0
        private set

    fun addToQueriesCount() {
        queries++
    }

    fun getMeasurements(
        days: Int,
        pattern: String,
        measureUnit: MeasureUnit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = measuresRepository.getMeasurements(days, measureUnit)
            val measurements = groupMeasuresByDateThenSum(result, pattern)

            withContext(Dispatchers.Main){
                _measurements.value = measurements
                mUnit = measureUnit.toString()
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
}