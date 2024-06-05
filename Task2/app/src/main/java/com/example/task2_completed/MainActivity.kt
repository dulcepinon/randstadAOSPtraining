package com.example.task2_completed

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val mForegroundHandler = Handler(Looper.getMainLooper())
    private val mBackgroundHandler = Handler(Looper.getMainLooper())

    private var startForegroundTime: Long = 0
    private var elapsedForegroundTime: Long = 0

    private var startBackgroundTime: Long = 0
    private var elapsedBackgroundTime: Long = 0

    private lateinit var foregroundTimerText: TextView
    private lateinit var backgroundTimerText: TextView

    private var foregroundTimer: Runnable = object : Runnable {
        override fun run() {
            updateForegroundTimer()
            mForegroundHandler.postDelayed(this, 1000)
        }
    }

    private var backgroundTimer: Runnable = object : Runnable {
        override fun run() {
            updateBackgroundTimer()
            mBackgroundHandler.postDelayed(this, 1000)
        }
    }
    private fun updateForegroundTimer() {
        val fgndTimeMillis = elapsedForegroundTime + (System.currentTimeMillis() - startForegroundTime)

        val fTimeInSeconds= fgndTimeMillis / 1000
        val foregroundHours = fTimeInSeconds/3600
        val foregroundMinutes = (fTimeInSeconds%3600)/60
        val foregroundSeconds = fTimeInSeconds%60

        val fFormattedTime = String.format("%02d:%02d:%02d",foregroundHours,foregroundMinutes,foregroundSeconds)
        foregroundTimerText.text = fFormattedTime
    }
    private fun updateBackgroundTimer() {
        val backgndTimeMillis = elapsedBackgroundTime + (System.currentTimeMillis() - startBackgroundTime)

        val bTimeInSeconds= backgndTimeMillis / 1000
        val backgroundHours = bTimeInSeconds/3600
        val backgroundMinutes = (bTimeInSeconds%3600)/60
        val backgroundSeconds = bTimeInSeconds%60

        val bFormattedTime = String.format("%02d:%02d:%02d",backgroundHours,backgroundMinutes,backgroundSeconds)
        backgroundTimerText.text = bFormattedTime
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        foregroundTimerText = findViewById(R.id.foregroundTimer)
        backgroundTimerText = findViewById(R.id.BackgroundTimer)

        startBackgroundTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        startForegroundTime = System.currentTimeMillis()
        mForegroundHandler.post(foregroundTimer)

        elapsedBackgroundTime += System.currentTimeMillis() - startBackgroundTime
        mBackgroundHandler.removeCallbacks(backgroundTimer)
    }

    override fun onPause() {
        super.onPause()
        elapsedForegroundTime += System.currentTimeMillis() - startForegroundTime
        mForegroundHandler.removeCallbacks(foregroundTimer)

        startBackgroundTime = System.currentTimeMillis()
        mBackgroundHandler.post(backgroundTimer)
    }

    override fun onDestroy() {
        super.onDestroy()
        mForegroundHandler.removeCallbacks(foregroundTimer)
        mBackgroundHandler.removeCallbacks(backgroundTimer)
    }
}