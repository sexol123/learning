package com.example.myapplication

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class PlayServiceConnection: ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }
}