package com.example.rafaellat.journaler.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.rafaellat.journaler.database.Crud
import com.example.rafaellat.journaler.database.Content
import com.example.rafaellat.journaler.model.MODE
import com.example.rafaellat.journaler.model.Note

class DatabaseService : IntentService("DatabaseService") {

    companion object {
        val EXTRA_ENTRY = "entry"
        val EXTRA_OPERATION = "operation"
    }

    private val tag = "Database service"

    override fun onCreate() {
        super.onCreate()
        Log.v(tag, "[ ON CREATE ]")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.v(tag, "[ ON LOW MEMORY ]")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(tag, "[ ON DESTROY ]")
    }

    override fun onHandleIntent(p0: Intent?) {
        p0?.let {
            val note = p0.getParcelableExtra<Note>(EXTRA_ENTRY)
            note?.let {
                val operation = p0.getIntExtra(EXTRA_OPERATION, -1)
                when (operation) {
                    MODE.CREATE.mode -> {
                        val result = Content.NOTE.insert(note)
                        if (result>0) {
                            Log.v(tag, "Note inserted.")
                        } else {
                            Log.v(tag, "Note not inserted.")
                        }
                        broadcastId(result)
                    }
                    MODE.EDIT.mode -> {
                        var result = 0L
                        try {
                            result = Content.NOTE.update(note)
                        } catch (e: Exception){
                            Log.e(tag, "Error: $e")
                        }
                        if (result>0) {
                            Log.v(tag, "Note inserted.")
                        } else {
                            Log.v(tag, "Note not inserted.")
                        }
                        broadcastId(result)
                    }
                    else -> {
                        Log.w(tag, "Unknown mode [$operation]")
                    }
                }
            }
        }
    }

    private fun broadcastId(id: Long) {
        val intent = Intent(Crud.BROADCAST_ACTION)
        intent.putExtra(Crud.BROADCAST_EXTRAS_KEY_CRUD_OPERATION_RESULT, id)
       sendBroadcast(intent)
    }
}

