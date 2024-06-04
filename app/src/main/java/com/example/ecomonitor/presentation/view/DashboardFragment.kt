package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anychart.APIlib
import com.anychart.AnyChart
import com.example.ecomonitor.R
import com.example.ecomonitor.databinding.FragmentDashboardBinding
import com.example.ecomonitor.presentation.viewmodel.DashboardViewModel

class DashboardFragment: Fragment() {
    private val viewModel: DashboardViewModel by viewModels()
    private var graphQueries = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

        initializeGraphOptions(binding)

        val consumptionChart = binding.consumptionChart
        val publicServiceChart = binding.publicServiceChart

        APIlib.getInstance().setActiveAnyChartView(consumptionChart)
        val cartesian = AnyChart.cartesian()
        consumptionChart.setChart(cartesian)

        viewModel.measurements.observe(viewLifecycleOwner) {
            APIlib.getInstance().setActiveAnyChartView(consumptionChart)
            cartesian.yAxis(0).title(viewModel.measureUnit)
            cartesian.removeAllSeries()

            val line = cartesian.line(it)
            line.color("#6dd43d")

            val column = cartesian.column(it)
            column.color("#6dd43d")
        }

        APIlib.getInstance().setActiveAnyChartView(publicServiceChart)
        val column = AnyChart.column()
        publicServiceChart.setChart(column)

        return binding.root
    }

    private fun initializeGraphOptions(binding: FragmentDashboardBinding) {
        val dateAdapter = createFromResource(R.array.spinner_date_options)
        binding.spinnerDate.adapter = dateAdapter

        val selectionAdapter = createFromResource(R.array.spinner_selection_options)
        binding.spinnerSelection.adapter = selectionAdapter

        binding.spinnerDate.onItemSelectedListener = onItemSelectedListener {
            val spinnerDate = binding.spinnerDate.selectedItem.toString()
            val spinnerSelection = binding.spinnerSelection.selectedItem.toString()

            if (graphQueries > 0) {
                consumptionGraphQuery(spinnerDate, spinnerSelection)
            }

            graphQueries++
        }

        binding.spinnerSelection.onItemSelectedListener = onItemSelectedListener {
            val spinnerDate = binding.spinnerDate.selectedItem.toString()
            val spinnerSelection = binding.spinnerSelection.selectedItem.toString()

            if (graphQueries > 0) {
                consumptionGraphQuery(spinnerDate, spinnerSelection)
            }

            graphQueries++
        }
    }

    private fun consumptionGraphQuery(spinnerDate: String, spinnerSelection: String) {
        var days: Int? = null
        days = when(spinnerDate) {
            "1 semana" -> 7
            "2 semanas" -> 14
            "1 mes" -> 30
            "3 meses" -> 90
            "6 meses" -> 180
            else -> 7
        }

        var pattern: String? = null
        pattern = when(spinnerDate) {
            "1 semana" -> "dd/MM"
            "2 semanas" -> "dd/MM"
            "1 mes" -> "dd/MM"
            "3 meses" -> "MM"
            "6 meses" -> "MM"
            else -> "dd/MM"
        }

        viewModel.getElectricalMeasurements(days, pattern)
    }

    private fun createFromResource(textArrayResId: Int): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            textArrayResId,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    private fun onItemSelectedListener(onItemSelected: () -> Unit): OnItemSelectedListener {
        return object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { onItemSelected() }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}