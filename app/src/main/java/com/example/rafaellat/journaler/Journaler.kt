package com.example.rafaellat.journaler

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.rafaellat.journaler.service.MainService

class Journaler: Application() {

    companion object {
        val tag = "Journaler"
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        Log.v(tag, "[ ON CREATE ]")
        startService()
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

    private fun startService(){
        val serviceIntent = Intent (this, MainService::class.java)
        startService(serviceIntent)
    }

    private fun stopService(){
        val serviceIntent = Intent (this, MainService::class.java)
        stopService(serviceIntent)
    }
}