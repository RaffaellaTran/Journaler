package com.example.rafaellat.journaler.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.rafaellat.journaler.execution.TaskExecutor

//get all services functionality
class MainService : Service(), DataSynchronization {

    private val tag = "Main service"
    private var binder = getServiceBinder()
    private var executor = TaskExecutor.getInstance(1)

    override fun onCreate() {
        super.onCreate()
        Log.v(tag, "[ ON CREATE]")
    }

    //this method is executed when the startService() method is triggered by some Android component. After the method is executed,
    // Android servie is started and can run in background.
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v(tag, "[ ON START COMMAND]")
        synchronize()
        return Service.START_STICKY //If the service is killed by the system or the user kills the application to which
        // the service belongs, the service will be recreated and restarted

    }

    // to bind to the service from another Android componentm use the bindService() method. After it binds,onBind() method
    //is executed.
    override fun onBind(intent: Intent?): IBinder? {
        Log.v(tag, "[ ON BIND]")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(tag, "[ ON UNBIND]")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        synchronize()
        super.onDestroy()
        Log.v(tag, "[ ON DESTROY]")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.v(tag, "[ ON LOW MEMORY]")
    }

    override fun synchronize() {
        executor.execute {
            Log.i(tag, "Synchronizing data [START]")
            Thread.sleep(3000)
            Log.i(tag, "Synchronizing data [END]")
        }
    }

    private fun getServiceBinder(): MainServiceBinder = MainServiceBinder()
    inner class MainServiceBinder : Binder() {
        fun getService(): MainService = this@MainService
    }
}