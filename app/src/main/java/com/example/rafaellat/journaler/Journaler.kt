package com.example.rafaellat.journaler

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.example.rafaellat.journaler.receiver.NetworkReceiver
import com.example.rafaellat.journaler.service.MainService

class Journaler : Application() {
    private val networkReceiver = NetworkReceiver()

    companion object {
        val tag = "Journaler"
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        Log.v(tag, "[ ON CREATE ]")
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)

    }

    //triggered in critical memory situations (actively running processes should trim their memory usage)
    override fun onLowMemory() {
        super.onLowMemory()
        Log.w(tag, "[ ON LOW MEMORY ]")
        // if we get low on memory we will stop service if running
        stopService()
    }

    //memory is trimmed
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(tag, "[ ON TRIM MEMORY ]: $level")
    }

    private fun stopService() {
        val serviceIntent = Intent(this, MainService::class.java)
        stopService(serviceIntent)
    }
}