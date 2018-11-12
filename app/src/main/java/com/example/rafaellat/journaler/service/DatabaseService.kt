package com.example.rafaellat.journaler.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.rafaellat.journaler.database.Db
import com.example.rafaellat.journaler.model.MODE
import com.example.rafaellat.journaler.model.Note

class DatabaseService: IntentService("DatabaseService") {

    companion object {
        val EXTRA_ENTRY = "entry"
        val EXTRA_OPERATION = "operation"
    }

    private val tag = "Database service"

    override fun onCreate() {
        super.onCreate()
        Log.v (tag, "[ ON CREATE ]")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.v (tag, "[ ON LOW MEMORY ]")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v (tag, "[ ON DESTROY ]")
    }

    override fun onHandleIntent(p0: Intent?) {
        p0?.let {
            val note = p0.getParcelableExtra<Note>(EXTRA_ENTRY)
            note?.let {
                val operation = p0.getIntExtra(EXTRA_OPERATION, -1)
                when (operation){
                    MODE.CREATE.mode -> {
                        val result = Db.insert(note)
                        if (result){
                            Log.v (tag, "Note inserted.")
                        } else {
                            Log.v (tag, "Note not inserted.")
                        }
                    }
                    MODE.EDIT.mode -> {
                        val result = Db.update(note)
                        if (result){
                            Log.v (tag, "Note inserted.")
                        } else {
                            Log.v (tag, "Note not inserted.")
                        }
                    }
                    else ->{
                        Log.w(tag, "Unknown mode [$operation]")
                    }
                }
            }
        }
    }
}

