package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Message
import android.util.Log
import java.util.concurrent.TimeUnit

class DownloadService: Service() {

    companion object{
        val tag: String = DownloadService::class.java.simpleName
    }

    private lateinit var mHandler: DownloadHandler

    override fun onCreate() {
        val thread = DownloadThread()
        thread.name = "Download Thread"
        thread.start()

        while (thread.downloadHandler == null){
            Thread.sleep(100)
        }

        mHandler = thread.downloadHandler!!
        mHandler.setService(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val song = intent?.getStringExtra(MainActivity.KEY_SONG)
        val message =  Message.obtain()
        message.obj = song
        message.arg1 = startId
        mHandler.sendMessage(message)

        return Service.START_REDELIVER_INTENT
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun downloadSong(song: String) {
        val endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10L)
        while (System.currentTimeMillis() < endTime){
            try {
                Thread.sleep(1000)
            }catch (e: Exception){
                Log.e(tag, "downloadSong: Song: $song"+e.message, e.fillInStackTrace())
            }
        }
        Log.d(tag, "Song downloaded!!! Song: $song")
    }
}