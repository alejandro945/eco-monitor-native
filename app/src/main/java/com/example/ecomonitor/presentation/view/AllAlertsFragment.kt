package com.example.ecomonitor.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecomonitor.databinding.AllAlertsFragmentBinding

class AllAlertsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AllAlertsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): AllAlertsFragment {
            return AllAlertsFragment()
        }
    }
}