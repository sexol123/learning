package com.example.myapplication

import android.app.ActivityOptions
import android.app.admin.DevicePolicyManager
import android.app.admin.DevicePolicyManager.LOCK_TASK_FEATURE_NONE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private lateinit var binding: ActivityMainBinding
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            mBound = true
            serviceMessenger = Messenger(binder!!)
            val message = Message.obtain()
            message.arg1 = 2
            message.arg2 = 1
            message.replyTo = activityMessanger
            try {
                serviceMessenger.send(message)

            }catch (e: Exception){
                Log.d(TAG, "onServiceConnected: ", e.fillInStackTrace())
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
            mBound = false
        }
    }
    private var mBound = false
    private lateinit var playerService: PlayerService
    private lateinit var serviceMessenger: Messenger
    private val activityMessanger = Messenger(ActivityHandler(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)



        initButton()
    }

    override fun onStart() {
        Log.d(TAG, "onStart: ")
        super.onStart()
        val intent = Intent(this, PlayerService::class.java)
        bindService(intent,serviceConnection ,Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        super.onStop()
        if (mBound){
            unbindService(serviceConnection)
            mBound = false
        }
    }

    public fun changePlayButtonText(text: String){
        binding.btnPlayMusic.text = text
    }


    private fun initButton() {


        binding.apply {
            btnDownload.setOnClickListener { b ->
                Toast.makeText(b.context, "${(b as Button).text} clicked", Toast.LENGTH_SHORT)
                    .show()

                for (song in Playlist.songsList){
                   /* val message = Message.obtain()
                    message.obj = song
                    thread.downloadHandler.sendMessage(message)*/
                    val intent = Intent(this@MainActivity, DownloadService::class.java)
                    intent.putExtra(Companion.KEY_SONG, song)
                    startService(intent)
                }

            }
            btnPlayMusic.setOnClickListener {
                Log.d(TAG, "btnPlayMusic clicked: ")
                if (mBound){
                    val intent = Intent(this@MainActivity, PlayerService::class.java)
                    startService(intent)
                    val message = Message.obtain()
                    message.arg1 = 2
                    message.replyTo = activityMessanger
                    try {
                        serviceMessenger.send(message)

                    }catch (e: Exception){
                        Log.d(TAG, "btnPlayMusic: ", e.fillInStackTrace())
                    }
                }else{
                    Log.e(TAG, "Not connected!!!!: ", )
                }
            }
        }
    }

    companion object {
        const val KEY_SONG: String = "key_song"
    }

}