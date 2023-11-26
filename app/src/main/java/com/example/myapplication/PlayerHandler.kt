package com.example.myapplication

import android.os.Handler
import android.os.Message
import android.os.RemoteException
import android.util.Log

class PlayerHandler constructor(private val playerService: PlayerService) : Handler() {
    private val TAG: String = this::class.java.simpleName

    override fun handleMessage(msg: Message) {
        when(msg.arg1){
            0 -> {
                playerService.play()
            }
            1 -> {
                playerService.pause()
            }
            2 -> {
                val isPlaying = if (playerService.isPlaying()) 1 else 0
                val message = Message.obtain()
                message.arg1 = isPlaying
                if (msg.arg2 == 1){
                    message.arg2 = 1
                }
                message.replyTo = playerService.messanger
               try {
                   msg.replyTo.send(message)
               }catch (e: RemoteException){
                   Log.e(TAG, "handleMessage: ",e.fillInStackTrace() )
               }
            }
        }
    }
}