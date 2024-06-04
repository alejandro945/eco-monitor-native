package com.example.ecomonitor.presentation.util

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

class UIUtil {
    companion object {
        fun spinnerAdapterFromResource(context: Context, textArrayResId: Int): ArrayAdapter<CharSequence> {
            val adapter = ArrayAdapter.createFromResource(
                context,
                textArrayResId,
                android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            return adapter
        }

        fun onItemSelectedListener(onItemSelected: () -> Unit): AdapterView.OnItemSelectedListener {
            return object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { onItemSelected() }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        fun showMessage(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}