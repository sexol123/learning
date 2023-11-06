package com.example.myapplication

import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.util.concurrent.TimeUnit

class DownloadThread: Thread() {

    public val tag = this.javaClass.simpleName
    public lateinit var downloadHandler: DownloadHandler

    override fun run() {
        Looper.prepare()
        downloadHandler = DownloadHandler(Looper.myLooper()!!)
        Looper.loop()
    }

}