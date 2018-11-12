package com.example.rafaellat.journaler.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

//This class is triggered when we boot or replace the application.
class BootReceiver : BroadcastReceiver() {

    val tag = "Boot receiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(tag, "Boot completed")
        // perform the boot stuff
    }
}