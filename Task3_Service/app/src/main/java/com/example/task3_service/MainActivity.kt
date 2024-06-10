package com.example.task3_service

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //upTimeText = findViewById(R.id.upTimeText)

        val startServiceButton = findViewById<Button>(R.id.startButton)
        startServiceButton.setOnClickListener{
            startService(Intent(this, SystemAwakeService::class.java))
        }

        val stopServiceButton = findViewById<Button>(R.id.stopButton)
        stopServiceButton.setOnClickListener {
            stopService(Intent(this, SystemAwakeService::class.java))
        }
    }

    override fun startService(service: Intent?): ComponentName? {
        Toast.makeText(this.baseContext, "on startService", Toast.LENGTH_SHORT).show()
        return super.startService(service)
    }

    override fun stopService(name: Intent?): Boolean {
        Toast.makeText(this.baseContext, "on stopService", Toast.LENGTH_SHORT).show()
        return super.stopService(name)
    }
}