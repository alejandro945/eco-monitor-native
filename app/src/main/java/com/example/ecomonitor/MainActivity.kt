package com.example.ecomonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAnalytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT){
            param(FirebaseAnalytics.Param.ITEM_ID, "id");
            param(FirebaseAnalytics.Param.ITEM_NAME, "name");
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        }
    }

}