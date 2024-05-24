package com.example.task3_service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class SystemAwakeService : Service() {

    //we need to specify what kind of service is
    override fun onBind(intent: Intent?): IBinder? {
        return null
    } // use to create a bound service

    //This function is triggered whenever another Android component sends an intent to this running service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Toast.makeText(this.baseContext, "onStartCommand_service", Toast.LENGTH_SHORT).show()

        val timeInMillis = SystemClock.elapsedRealtime();
        val timeInSeconds = timeInMillis/1000
        val hours = timeInSeconds/3600
        val minutes = (timeInSeconds%3600)/60
        val seconds = timeInSeconds%60

        val formattedTime = String.format("%02d:%02d:%02d",hours,minutes,seconds)
        println("The system has been up for: $formattedTime")

        return START_STICKY
    }
}