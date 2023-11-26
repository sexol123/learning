package com.example.myapplication

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import java.util.concurrent.TimeUnit

class DownloadHandler(looper: Looper): Handler(looper) {

    public val tag = this.javaClass.simpleName
    private var mService:DownloadService? = null

    override fun handleMessage(msg: Message) {
        val song = msg.obj.toString()
        downloadSong(song)
        mService?.stopSelf(msg.arg1)
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

    fun setService(downloadService: DownloadService) {
        mService = downloadService
    }

}