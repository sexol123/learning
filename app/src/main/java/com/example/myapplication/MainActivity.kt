package com.example.myapplication

import android.app.ActivityOptions
import android.app.admin.DevicePolicyManager
import android.app.admin.DevicePolicyManager.LOCK_TASK_FEATURE_NONE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Message
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

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)



        initButton()
    }

    private fun initButton() {
        val thread = DownloadThread().apply {
            name = "Download Thread"
            start()
        }


        binding.apply {
            btnDownload.setOnClickListener { b ->
                Toast.makeText(b.context, "${(b as Button).text} clicked", Toast.LENGTH_SHORT)
                    .show()

                for (song in Playlist.songsList){
                    val message = Message.obtain()
                    message.obj = song
                    thread.downloadHandler.sendMessage(message)
                }

            }
            btnPlayMusic.setOnClickListener {

            }
        }
    }


}