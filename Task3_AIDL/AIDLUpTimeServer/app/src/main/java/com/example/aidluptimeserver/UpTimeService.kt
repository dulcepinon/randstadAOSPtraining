package com.example.aidluptimeserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.text.format.DateUtils
import android.util.Log

class UpTimeService : Service() {

    private val tag = "UpTimeService"

    private val mBinder = object : IAIDLUpTimeInterface.Stub() {
        override fun getUpTime(): String {

            val timeInMillis = SystemClock.elapsedRealtime()
            val elapsedTime = DateUtils.formatElapsedTime(timeInMillis/ 1000)

            Log.d(tag, "Booting time: $elapsedTime")

            return elapsedTime
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }
}