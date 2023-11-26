package com.example.myapplication

import android.app.IntentService
import android.content.Intent

class DownloadIntentService(name: String? = "DownloadIntentService") : IntentService(name) {

    init {
        setIntentRedelivery(true)
    }

    constructor():this("DownloadIntentService"){

    }
    override fun onHandleIntent(intent: Intent?) {

    }
}