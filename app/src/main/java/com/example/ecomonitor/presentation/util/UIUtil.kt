package com.example.ecomonitor.presentation.util

import android.content.Context
import android.widget.Toast

class UIUtil {
    companion object {
        fun showMessage(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}