package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.ecomonitor.databinding.FragmentDashboardBinding

class DashboardFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

        val consumptionChart = binding.consumptionChart
        APIlib.getInstance().setActiveAnyChartView(consumptionChart)
        consumptionChart.setChart(AnyChart.line())

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