package com.example.aidluptimeclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aidluptimeserver.IAIDLUpTimeInterface


class MainActivity : AppCompatActivity() {

    private var mServiceAidl: IAIDLUpTimeInterface? = null
    private lateinit var upTimeText: TextView
    val tag = "MainActivity"

    //Service connection object
    private val mConnection: ServiceConnection = object : ServiceConnection {

        // Called when the connection with the service is established.
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            try {
                // It gets an instance of the IAIDLUpTimeInterface, which it is use to call on the service.
                mServiceAidl = IAIDLUpTimeInterface.Stub.asInterface(service)
                Log.d(tag, "Service connected")

            } catch (e: RemoteException){

            }
        }

        // Called when the connection with the service disconnects unexpectedly.
        override fun onServiceDisconnected(name: ComponentName) {
            mServiceAidl = null
            Log.d(tag, "Service has disconnected")
        }
    }

    private fun doBindService(){
        val intent = Intent("UpTimeService")
        intent.setPackage("com.example.aidluptimeserver")
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val upTimeButton = findViewById<Button>(R.id.UpTimeButton)
        upTimeText = findViewById(R.id.UpTimeText)

        doBindService()
        upTimeButton.setOnClickListener {
            try{
                val bootTime = mServiceAidl!!.getUpTime()
                Log.d(tag, "Booting time : $bootTime")
                upTimeText.text = bootTime

            } catch(e: RemoteException){

            }
        }
    }
}