package com.example.ecomonitor.data.observability

import android.content.Context
import com.example.ecomonitor.domain.interfaces.IObservability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class FirebaseAnalytics(private val context: Context): IObservability {

    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun log(eventName: String) {
        firebaseAnalytics.logEvent(eventName){
            param(FirebaseAnalytics.Param.ITEM_ID, "id");
            param(FirebaseAnalytics.Param.ITEM_NAME, "name");
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        }
    }

}