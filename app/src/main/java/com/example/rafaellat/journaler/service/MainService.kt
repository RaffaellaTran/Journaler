package com.example.rafaellat.journaler.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.rafaellat.journaler.api.BackendServiceHeaderMap
import com.example.rafaellat.journaler.api.JournalerBackendService
import com.example.rafaellat.journaler.api.TokenManager
import com.example.rafaellat.journaler.api.UserLoginRequest
import com.example.rafaellat.journaler.database.Content
import com.example.rafaellat.journaler.execution.TaskExecutor
import com.example.rafaellat.journaler.model.Note
import com.example.rafaellat.journaler.model.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            var headers = BackendServiceHeaderMap.obtain()
            val service = JournalerBackendService.obtain()
            val credentials = UserLoginRequest("username", "password")
            // sync call
            val tokenResponse = service.login(headers, credentials).execute()

            if (tokenResponse.isSuccessful) {
                val token = tokenResponse.body()
                token?.let {
                    TokenManager.currentToken = token
                    headers = BackendServiceHeaderMap.obtain(true)
                    // async calls
                    fetchNotes(service, headers)
                    fetchTodos(service, headers)

                }
            }
            // Thread.sleep(3000)
            Log.i(tag, "Synchronizing data [END]")
        }
    }

    /**
     * Fetches notes asynchronously.
     */
    private fun fetchNotes(service: JournalerBackendService, headers: Map<String, String>) {
        service
            .getNotes(headers)
            .enqueue(object : Callback<List<Note>> {
                override fun onResponse(call: Call<List<Note>>?, response: Response<List<Note>>?) {
                    response?.let {
                        if (response.isSuccessful) {
                            val notes = response.body()
                            notes?.let {
                                Content.NOTE.insert(notes)
                            }
                        }
                    }
                }

                override fun onFailure(
                    call:
                    Call<List<Note>>?, t: Throwable?
                ) {
                    Log.e(tag, "We couldn't fetch notes.")
                }
            }
            )
    }

    /**
     * Fetches TODOs asynchronously.
     */
    private fun fetchTodos(service: JournalerBackendService, headers: Map<String, String>) {
        service
            .getTodos(headers)
            .enqueue(
                object : Callback<List<Todo>> {
                    override fun onResponse(
                        call: Call<List<Todo>>?, response: Response<List<Todo>>?
                    ) {
                        response?.let {
                            if (response.isSuccessful) {
                                val todos = response.body()
                                todos?.let {
                                    Content.TODO.insert(todos)
                                }
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<List<Todo>>?, t: Throwable?
                    ) {
                        Log.e(tag, "We couldn't fetch notes.")
                    }
                }
            )
    }

    private fun getServiceBinder(): MainServiceBinder = MainServiceBinder()
    inner class MainServiceBinder : Binder() {
        fun getService(): MainService = this@MainService
    }
}