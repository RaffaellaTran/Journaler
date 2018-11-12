package com.example.rafaellat.journaler.receiver

import android.content.*
import android.net.ConnectivityManager
import android.os.IBinder
import android.util.Log
import com.example.rafaellat.journaler.service.MainService

// This receiver will receive messages when a connectivity event occurs
class NetworkReceiver : BroadcastReceiver() {

    private val tag = "Network receiver"
    private var service: MainService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            if (binder is MainService.MainServiceBinder) {
                service = binder.getService()
                service?.synchronize()
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            val isConnected = activeNetwork != null

            if (isConnected) {
                Log.v(tag, "Connectivity  [AVAILABLE]")
                if (service == null) {
                    val intent = Intent(context, MainService::class.java)
                    context.bindService(intent, serviceConnection, android.content.Context.BIND_AUTO_CREATE)
                } else {
                    service?.synchronize()
                }
            } else {
                Log.v(tag, "Connectivity  [UNAVAILABLE]")
                context.unbindService(serviceConnection)
            }
        }
    }
}
