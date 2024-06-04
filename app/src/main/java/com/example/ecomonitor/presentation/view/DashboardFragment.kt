package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anychart.APIlib
import com.anychart.AnyChart
import com.example.ecomonitor.databinding.FragmentDashboardBinding
import com.example.ecomonitor.presentation.viewmodel.DashboardViewModel

class DashboardFragment: Fragment() {
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

        viewModel.measurements.observe(viewLifecycleOwner) {
            val consumptionChart = binding.consumptionChart
            APIlib.getInstance().setActiveAnyChartView(consumptionChart)

            val line = AnyChart.line()
            line.line(it)

            consumptionChart.setChart(line)
        }

        //TODO. Place various options in a ComboBox to display both Electrical Measurements and Water Measurements.
        viewModel.getElectricalMeasurements(30,"dd/MM")

        val publicServiceChart = binding.publicServiceChart
        APIlib.getInstance().setActiveAnyChartView(publicServiceChart)
        publicServiceChart.setChart(AnyChart.column())

        return binding.root
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}