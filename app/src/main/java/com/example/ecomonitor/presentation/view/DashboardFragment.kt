package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.FragmentDashboardBinding
import com.example.ecomonitor.domain.enum.MeasureUnit
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.util.UIUtil.Companion.onItemSelectedListener
import com.example.ecomonitor.presentation.viewmodel.DashboardViewModel

class DashboardFragment: Fragment() {
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

        initializeConsumptionChart(binding.consumptionChart)
        initializePublicServiceChart(binding.publicServiceChart)

        initializeSpinner(binding.spinnerDate, R.array.spinner_date_options)
        initializeSpinner(binding.spinnerSelection, R.array.spinner_selection_options)

        binding.spinnerDate.onItemSelectedListener = onItemSelectedListener { onSelection(binding) }
        binding.spinnerSelection.onItemSelectedListener = onItemSelectedListener { onSelection(binding) }

        viewModel.getServicesMeasurements(7, "dd/MM")

        return binding.root
    }

    private fun initializeConsumptionChart(consumptionChart: AnyChartView) {
        APIlib.getInstance().setActiveAnyChartView(consumptionChart)
        val cartesian = AnyChart.cartesian()
        consumptionChart.setChart(cartesian)

        viewModel.measurements.observe(viewLifecycleOwner) { list ->
            showQueryData(consumptionChart, cartesian, list)
        }
    }

    private fun showQueryData(
        consumptionChart: AnyChartView,
        cartesian: Cartesian,
        list: List<ValueDataEntry>
    ) {
        APIlib.getInstance().setActiveAnyChartView(consumptionChart)
        cartesian.yAxis(0).title(viewModel.mUnit)
        cartesian.removeAllSeries()

        val line = cartesian.line(list)
        line.color("#6dd43d")

        val column = cartesian.column(list)
        column.color("#6dd43d")
    }

    private fun initializePublicServiceChart(publicServiceChart: AnyChartView) {
        APIlib.getInstance().setActiveAnyChartView(publicServiceChart)
        val column = AnyChart.column()
        publicServiceChart.setChart(column)

        viewModel.servicesMeasurements.observe(viewLifecycleOwner) { map ->
            showPublicServicesData(publicServiceChart, column, map)
        }
    }
    private fun showPublicServicesData(
        publicServiceChart: AnyChartView,
        column: Cartesian,
        map: MutableMap<String, List<ValueDataEntry>>
    ) {
        APIlib.getInstance().setActiveAnyChartView(publicServiceChart)
        column.yAxis(0).title("Consumption KWH vs. M3")
        column.removeAllSeries()

        val kwhColumn = column.column(map["kwh"])
        kwhColumn.color("#ffbb38")

        val m3Column = column.column(map["m3"])
        m3Column.color("#396aff")
    }

    private fun initializeSpinner(spinner: Spinner, textArrayResId: Int) {
        val adapter = UIUtil.spinnerAdapterFromResource(requireContext(), textArrayResId)
        spinner.adapter = adapter
    }

    private fun onSelection(binding: FragmentDashboardBinding) {
        val spinnerDate = binding.spinnerDate.selectedItem.toString()
        val spinnerSelection = binding.spinnerSelection.selectedItem.toString()

        if (viewModel.queries > 0) {
            queryConsumptionView(spinnerDate, spinnerSelection)
        }

        viewModel.addToQueriesCount()
    }

    private fun queryConsumptionView(spinnerDate: String, spinnerSelection: String) {
        val days = when(spinnerDate) {
            "1 semana" -> 7
            "2 semanas" -> 14
            "1 mes" -> 30
            "3 meses" -> 90
            "6 meses" -> 180
            else -> 7
        }

        val pattern = when(spinnerDate) {
            "1 semana" -> "dd/MM"
            "2 semanas" -> "dd/MM"
            "1 mes" -> "dd/MM"
            "3 meses" -> "MM"
            "6 meses" -> "MM"
            else -> "dd/MM"
        }

        val measureUnit = when(spinnerSelection) {
            "Electricidad/día - kWh" -> MeasureUnit.KWH
            "Agua/día - m3" -> MeasureUnit.M3
            else -> MeasureUnit.KWH
        }

        viewModel.getMeasurements(days, pattern, measureUnit)
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}