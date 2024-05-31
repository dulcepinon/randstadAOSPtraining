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

    private val mHandler = Handler(Looper.getMainLooper()) //variable type: handler, initialize with a handler that operates in the main looper
    private var timeInSecondsForeground: Long = 0
    private var timeInSecondsBackground: Long = 0
    private lateinit var foregroundTimerText: TextView
    private lateinit var backgroundTimerText: TextView

    private var foregroundTimeRunning: Runnable = object : Runnable {
        override fun run() {
            timeInSecondsForeground++
            updateForegroundTimer(timeInSecondsForeground)
            mHandler.postDelayed(this, 1000)
        }
    }

    private var backgroundTimeRunning: Runnable = object : Runnable {
        override fun run() {
            timeInSecondsBackground++
            updateBackgroundTimer(timeInSecondsBackground)
            mHandler.postDelayed(this, 1000)
        }
    }

    private fun updateBackgroundTimer(fTimeInSeconds: Long) {
        val foregroundHours = fTimeInSeconds/3600
        val foregroundMinutes = (fTimeInSeconds%3600)/60
        val foregroundSeconds = fTimeInSeconds%60

        val formattedTime = String.format("%02d:%02d:%02d",foregroundHours,foregroundMinutes,foregroundSeconds)
        backgroundTimerText.text = formattedTime
    }

    private fun updateForegroundTimer(fTimeInSeconds: Long) {
        val foregroundHours = fTimeInSeconds/3600
        val foregroundMinutes = (fTimeInSeconds%3600)/60
        val foregroundSeconds = fTimeInSeconds%60

        val formattedTime = String.format("%02d:%02d:%02d",foregroundHours,foregroundMinutes,foregroundSeconds)
        foregroundTimerText.text = formattedTime
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        foregroundTimerText = findViewById(R.id.foregroundTimer)
        backgroundTimerText = findViewById(R.id.BackgroundTimer)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        //Toast.makeText(this, "Inside onResume", Toast.LENGTH_SHORT).show()
        mHandler.post(foregroundTimeRunning)
        mHandler.removeCallbacks(backgroundTimeRunning)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(foregroundTimeRunning)
        mHandler.post(backgroundTimeRunning)
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "Inside onStop", Toast.LENGTH_SHORT).show()
        //mHandler.removeCallbacks(foregroundTimeRunning)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(foregroundTimeRunning)
        mHandler.removeCallbacks(backgroundTimeRunning)
    }
}