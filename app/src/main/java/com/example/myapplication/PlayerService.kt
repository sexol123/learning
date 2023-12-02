package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.content.res.Resources
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Messenger
import android.util.Log

const val NOTIFICATION_CHANNEL_ID = 11
const val NOT_CHAN_ID = "Play Music Chanel"

class PlayerService : Service() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mediaPlayer: MediaPlayer
    public val messanger = Messenger(PlayerHandler(this))
    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        mediaPlayer = MediaPlayer.create(this, R.raw.davaj_sbezhim_iskorki)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val nb: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, NOT_CHAN_ID)
        } else {
            Notification.Builder(this)
        }
        nb.setSmallIcon(R.mipmap.ic_launcher_round)
        nb.setCategory(Notification.CATEGORY_SERVICE)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(NOT_CHAN_ID, "Main Chanel", NotificationManager.IMPORTANCE_HIGH)
            val nm = getSystemService(NotificationManager::class.java) as NotificationManager
            notificationChannel.description = "kjebfwekjfbwekfbwekj wekfbwekjfb"
            nm.createNotificationChannel(notificationChannel)
        } else {
            ///VERSION.SDK_INT < O
        }

        val notification = nb.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_CHANNEL_ID,
                notification,
                FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_CHANNEL_ID, notification)
        }



        mediaPlayer.setOnCompletionListener {
            stopSelf()
            stopForeground(STOP_FOREGROUND_REMOVE)
        }
        return Service.START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: ")
        return messanger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        mediaPlayer.release()
        super.onDestroy()
    }

    //Client's methods

    public fun play() {
        mediaPlayer.start()
    }

    public fun pause() {
        mediaPlayer.pause()
    }

    public fun isPlaying() = mediaPlayer.isPlaying

}