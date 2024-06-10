package com.example.aidluptimeclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aidluptimeserver.IAIDLUpTimeInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var mUpTimeService: IAIDLUpTimeInterface? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var upTimeText: TextView
    var mServiceIsBound = false
    val tag = "MainActivity"

    //Service connection object
    private val mConnection: ServiceConnection = object : ServiceConnection {

        // Called when the connection with the service is established.
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            try {
                // It gets an instance of the IAIDLUpTimeInterface, which it is use to call on the service.
                mUpTimeService = IAIDLUpTimeInterface.Stub.asInterface(service)
                mServiceIsBound = true
                updateTime()

                Log.d(tag, "Service connected")

            } catch (e: RemoteException){
                e.printStackTrace()
            }
        }

        // Called when the connection with the service disconnects unexpectedly.
        override fun onServiceDisconnected(name: ComponentName) {
            mUpTimeService = null
            mServiceIsBound = false
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
        setContentView(R.layout.activity_main)

        upTimeText = findViewById(R.id.UpTimeText)
        doBindService()
    }

    private fun updateTime() {
        scope.launch {
            while (mServiceIsBound)
            {
                try {
                    val bootTime = mUpTimeService?.getUpTime() ?: "00:00"
                    upTimeText.text = bootTime
                    Log.d(tag, "Booting time : $bootTime")
                    delay(100)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }
   }

    override fun onDestroy() {
        super.onDestroy()
        if(mServiceIsBound) {
            unbindService(mConnection)
            mServiceIsBound = false
        }
        scope.cancel()
    }
}

