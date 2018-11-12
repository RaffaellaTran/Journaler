package com.example.rafaellat.journaler.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

//This class is triggered when we turn off the device.
class ShutdownReceiver : BroadcastReceiver() {

    val tag = "Shutdown receiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(tag, "Shutting down.")
        //perform the cleanup
    }

}