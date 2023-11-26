package com.example.myapplication

import android.os.Handler
import android.os.Message
import android.util.Log

class ActivityHandler(private val mainActivity: MainActivity): Handler() {
    private val TAG: String = javaClass.simpleName



    override fun handleMessage(msg: Message) {
        if (msg.arg1 == 0){
            if (msg.arg2 == 1){
                mainActivity.changePlayButtonText("Play")
            }else{
                val message = Message.obtain()
                message.arg1 = 0
                try {
                    msg.replyTo.send(message)
                }catch (e: Exception){
                    Log.d(TAG, "handleMessage: ", e.fillInStackTrace())
                }
                mainActivity.changePlayButtonText("Pause")
            }

        } else if (msg.arg1 == 1){
            if (msg.arg2 == 1){
                mainActivity.changePlayButtonText("Pause")
            }else{
                val message = Message.obtain()
                message.arg1 = 1
                try {
                    msg.replyTo.send(message)
                }catch (e: Exception){
                    Log.d(TAG, "handleMessage: ", e.fillInStackTrace())
                }
                mainActivity.changePlayButtonText("Play")
            }
        }
    }
}